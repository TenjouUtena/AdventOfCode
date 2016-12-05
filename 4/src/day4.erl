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

code(Code) ->
  string:left(lists:map(fun (X) -> element(1,X) end, lists:reverse(
    lists:keysort(2,
      lists:map(fun (X) -> {X, count(X,string:strip(lists:sort(Code),both,$-))} end,
        lists:reverse(lists:usort(Code)))))),5).


decode($-, _) ->
  32;
decode(Char, Key) when (Char >= $a) and (Char =< $z) ->
  (((Char - $a)+Key) rem 26) + ($a);
decode(Char, _) ->
  Char.

evaluate_codes(Head) ->
  case re:run(Head, "([a-z\\-]+)([0-9]+)\\[([a-z]{5})\\]", [{capture,[1,2,3],list}]) of
    {match, Data} ->
      %%io:format("~s ~s ~s ~s ~n",Data ++ [code(lists:nth(1,Data))]),
      [CodeName, _, Check] = Data,
      {Key, _} =  string:to_integer(lists:nth(2,Data)),
      case Check == code(CodeName) of
        true ->
          Decoded = lists:map(fun (X) -> decode(X,Key) end, CodeName),
          case string:left(Decoded,5) == "north" of
            true ->
              io:format("~s ~w ~n",[Decoded, Key]);
            false -> ok
          end,
          Key;
        false -> 0
      end;
    nomatch -> 0
  end.


run_day4(Filename) ->
  case file: read_file(Filename) of
    {ok, Data} ->
      lists:foldl(fun (X, Y) -> evaluate_codes(X) + Y end,0,(binary:split(Data, [<<"\r\n">>],[global])));
    {error, _} ->
      throw("error")
  end.