%%%-------------------------------------------------------------------
%%% @author Utena
%%% @copyright (C) 2016, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 02. Dec 2016 11:43 AM
%%%-------------------------------------------------------------------
-module(part2).
-author("Utena").
-behavior(gen_statem).

%% API
-export([]).


%% States

-export([start/0,stop/0]).
-export([terminate/3,code_change/4,init/1,callback_mode/0]).
-export([path/1, show/0]).
-export([handle_event/4]).


%% API.  This example uses a registered name name()
%% and does not link to the caller.
start() ->
  gen_statem:start({local,name()}, ?MODULE, [], []).
stop() ->
  gen_statem:stop(name()).

name() -> aoc_2_part2.

%% Stuff I need
terminate(_Reason, _State, _Data) ->
  void.
code_change(_Vsn, State, Data, _Extra) ->
  {ok,State,Data}.
init(_) ->
  {ok, five, ok}.

path([Dir|Rest]) ->
  gen_statem:cast(name(), {direction, Dir}),
  path(Rest);
path([]) ->
  ok.

show() ->
  gen_statem:cast(name(), {show_state}).

callback_mode() -> handle_event_function.

handle_event(_, {show_state}, State, _) ->
  io:format("~w~n",[State]),
  {keep_state, ok};

handle_event(_, {direction, Dir}, State, _) ->

  case State of
    one ->
      case Dir of
        $D -> {next_state, three, ok};
        _ -> {keep_state, ok}
      end;
    two ->
      case Dir of
        $R -> {next_state, three, ok};
        $D -> {next_state, six, ok};
        _ -> {keep_state, ok}
      end;
    three ->
      case Dir of
        $U -> {next_state, one, ok};
        $D -> {next_state, seven, ok};
        $R -> {next_state, four, ok};
        $L -> {next_state, two, ok};
        _ -> {keep_state, ok}
      end;
    four ->
      case Dir of
        $D -> {next_state, eight, ok};
        $L -> {next_state, three, ok};
        _ -> {keep_state, ok}
      end;
    five ->
      case Dir of
        $R -> {next_state, six, ok};
        _ -> {keep_state, ok}
      end;
    six ->
      case Dir of
        $L -> {next_state, five, ok};
        $R -> {next_state, seven, ok};
        $U -> {next_state, two, ok};
        $D -> {next_state, alpha, ok};
        _ -> {keep_state, ok}
      end;
    seven ->
      case Dir of
        $U -> {next_state, three, ok};
        $D -> {next_state, beta, ok};
        $R -> {next_state, eight, ok};
        $L -> {next_state, six, ok};
        _ -> {keep_state, ok}
      end;
    eight ->
      case Dir of
        $U -> {next_state, four, ok};
        $D -> {next_state, charlie, ok};
        $R -> {next_state, nine, ok};
        $L -> {next_state, seven, ok};
        _ -> {keep_state, ok}
      end;
    nine ->
      case Dir of
        $L -> {next_state, eight, ok};
        _ -> {keep_state, ok}
      end;
    alpha ->
      case Dir of
        $U -> {next_state, six, ok};
        $R -> {next_state, beta, ok};
        _ -> {keep_state, ok}
      end;
    beta ->
      case Dir of
        $U -> {next_state, seven, ok};
        $D -> {next_state, delta, ok};
        $R -> {next_state, charlie, ok};
        $L -> {next_state, alpha, ok};
        _ -> {keep_state, ok}
      end;
    charlie ->
      case Dir of
        $U -> {next_state, eight, ok};
        $L -> {next_state, beta, ok};
        _ -> {keep_state, ok}
      end;
    delta ->
      case Dir of
        $U -> {next_state, beta, ok};
        _ -> {keep_state, ok}
      end
  end.

