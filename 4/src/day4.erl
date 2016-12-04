%%%-------------------------------------------------------------------
%%% @author Utena
%%% @copyright (C) 2016, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 04. Dec 2016 1:37 PM
%%%-------------------------------------------------------------------
-module(day4).
-author("Utena").

%% API
-export([code/1, run_day4/1]).

count(X, Y) -> count(X, Y, 0).

count(_, [], Count) -> Count;
count(X, [X|Rest], Count) -> count(X, Rest, Count+1);
count(X, [_|Rest], Count) -> count(X, Rest, Count).

countlist(Code) -> countlist(Code, [], lists:reverse(lists:usort(Code))).

countlist(_, Keylist, []) ->
  Keylist;
countlist(Code, Keylist, [Head|Rest]) ->
  countlist(Code, Keylist ++ [{Head, count(Head, Code)}], Rest).


code(Code) ->
  string:left(lists:map(fun (X) -> element(1,X) end, lists:reverse(lists:keysort(2,countlist(string:strip(lists:sort(Code),both,$-))))),5).


decode($-, _) ->
  $ ;
decode(Char, Key) when (Char >= $a) and (Char =< $z) ->
  (((Char - ($a - 1))+Key) rem 26) + ($a - 1);
decode(Char, _) ->
  Char.


evaluate_codes(R) -> evaluate_codes(R, 0).

evaluate_codes([], S) ->
  S;
evaluate_codes([Head|Rest], S) ->
  case re:run(Head, "([a-z\\-]+)([0-9]+)\\[([a-z]{5})\\]", [{capture,[1,2,3],list}]) of
    {match, Data} ->
      %% io:format("~s ~s ~s ~s ~n",Data ++ [code(lists:nth(1,Data))]),
      case lists:nth(3, Data) == code(lists:nth(1,Data)) of
        true ->
          Key = element(1,string:to_integer(lists:nth(2,Data))),
          Decoded = lists:map(fun (X) -> decode(X,Key) end,lists:nth(1,Data)),
          case string:left(Decoded,5) == "north" of
            true ->
              io:format("~s ~s ~n",[Decoded,lists:nth(2,Data)]);
            false ->
              ok
          end,
          S + element(1,string:to_integer(lists:nth(2,Data)));
        false ->
          S
      end;
    nomatch ->
      S
  end + evaluate_codes(Rest).


run_day4(Filename) ->
  case file: read_file(Filename) of
    {ok, Data} ->
      evaluate_codes(binary:split(Data, [<<"\r\n">>],[global]));
    {error, _} ->
      throw("error")
  end.