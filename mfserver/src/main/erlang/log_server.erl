-module(log_server).
-export([start_server/1]).

start_server(LogFile) ->
  {ok, ServerSocket} = gen_tcp:listen(8081, [binary, {packet, http}, {active, false}]),
  spawn(fun() -> loop(LogFile, ServerSocket) end).

loop(LogFile, ServerSocket) ->
  {ok, ClientSocket} = gen_tcp:accept(ServerSocket),
  spawn(fun() -> handle_request(LogFile, ClientSocket) end),
  loop(LogFile, ServerSocket).

handle_request(LogFile, ClientSocket) ->
  {ok, {http_request, Method, Path, Version}} = gen_tcp:recv(ClientSocket, 0),
  io:format("Server received request: ~p~n", [[Method, Path]]),
  {ok, File} = file:open(LogFile, read),
  Data = read_log(File, []),
  LastRows = lists:reverse(get_elements(lists:reverse(Data), 300, [])),
  Response = ["HTTP/1.1 200 OK\r\nConnection: close\r\nContent-Type: text/html\r\ncharset=UTF-8\r\nCache-Control: no-cache\r\n\r\n"],
  gen_tcp:send(ClientSocket, Response ++ LastRows),
  gen_tcp:close(ClientSocket).

read_log(File, Data) ->
  case Line = io:get_line(File, "") of
    eof ->
      Data;
    _ ->
      read_log(File, Data ++ [Line ++ "<br>"])
  end.

get_elements(List, 0,  Result) ->
  Result;
get_elements(List, Elements, Result) ->
  [H|T] = List,
  get_elements(T, Elements-1, Result ++ [H]).
