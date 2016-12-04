%%%-------------------------------------------------------------------
%%% @author Utena
%%% @copyright (C) 2016, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 02. Dec 2016 11:52 PM
%%%-------------------------------------------------------------------
-module(day3).
-author("Utena").

%% API
-export([load_triangles/1]).



find_triangles([], Valid) ->
  Valid;

find_triangles([Head|Rest], Valid) ->
  Tri = lists:map(fun(L) -> list_to_integer(binary_to_list(L)) end,re:split(re:replace(Head, "(^\\s+)|(\\s+$)", "", [global,{return,list}]),"[ ]+")),
  Max = lists:max(Tri),
  Tri2 = lists:delete(Max, Tri),
  Tot = lists:foldl(fun(X,Y) -> X+Y end, 0, Tri2),
  case Tot > Max of
    true ->
      find_triangles(Rest, Valid+1);
    false ->
      find_triangles(Rest, Valid)
  end.



load_triangles(Filename) ->
  case file: read_file(Filename) of
    {ok, Data} ->
      find_triangles(binary:split(Data, [<<"\r\n">>],[global]),0);
    {error, _} ->
      throw("error")
  end.
