%%%-------------------------------------------------------------------
%%% @author Utena
%%% @copyright (C) 2016, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 02. Dec 2016 6:47 PM
%%%-------------------------------------------------------------------
-module(p2dirg).
-author("Utena").

%% API
-export([load_graph/1, print_graph/1, find_exit/3, run_path/3, do_stuff/1]).


load_dict_line(L, Dict, Col) ->
  load_dict_line(L, Dict, Col, 0).

load_dict_line([],Dict,_,_) ->
  Dict;
load_dict_line([H|Rest], Dict, Col, Row) ->
  Dict2 = dict:store({Row,Col}, H, Dict),
  load_dict_line(Rest, Dict2, Col, Row+1).


process_graph_lines(L) ->
  Dict = dict:new(),
  process_graph_lines(L,0, Dict).

process_graph_lines([],_,Dict) ->
  Dict;
process_graph_lines([Line|Rest], Line_no, Dict) ->
  Dict2 = load_dict_line(binary_to_list(Line), Dict, Line_no),
  process_graph_lines(Rest, Line_no+1, Dict2).


load_graph_dict(Filename) ->
  case file:read_file(Filename) of
    {ok, Data} ->
      process_graph_lines(binary:split(Data, [<<"\r\n">>],[global]));
    {error, _} ->
      throw("error")
  end.


test_node(Row, Col, Dict, Graph, V1, Ind) ->
  case dict:is_key({Row,Col}, Dict) of
    true ->
      case dict:fetch({Row, Col}, Dict) of
        32 -> ok;
        Val ->
          V2 = digraph:add_vertex(Graph, Val),
          digraph:add_edge(Graph, V1, V2, Ind)
      end;
    false ->
      ok
  end.


make_g_node(L,D) ->
  Graph = digraph:new(),
  make_g_node(L, D, Graph).

make_g_node([], _, Graph) ->
  Graph;
make_g_node([H|Rest], Dict, Graph) ->
  {Row, Col} = H,
  case dict:fetch(H,Dict) of
    32 -> ok;
    Node ->
      Vert = digraph:add_vertex(Graph, Node),
      test_node(Row-1, Col, Dict, Graph, Vert, $L),
      test_node(Row+1, Col, Dict, Graph, Vert, $R),
      test_node(Row, Col-1, Dict, Graph, Vert, $U),
      test_node(Row, Col+1, Dict, Graph, Vert, $D)
  end,
  make_g_node(Rest, Dict, Graph).

load_graph(Filename) ->
  Dddd= load_graph_dict(Filename),
  make_g_node(dict:fetch_keys(Dddd),Dddd).

print_vertex([],_) ->
  ok;
print_vertex([Head|Rest],Graph) ->
  io:format("~w~n",[Head]),
  io:format("~w~n",[digraph:out_edges(Graph,Head)]),
  print_vertex(Rest,Graph).

print_graph(Graph) ->
  print_vertex(digraph:vertices(Graph),Graph).

is_exit([],_,_,Node) ->
  Node;
is_exit([Head|Rest], Dir, Graph, Node) ->
  case digraph:edge(Graph,Head) of
    {_, _, Exit, Dir} -> Exit;
    _ -> is_exit(Rest, Dir, Graph, Node)
  end.

find_exit(Node, Dir, Graph) ->
  is_exit(digraph:out_edges(Graph,Node),Dir, Graph, Node).


do_path([],_,Node) ->
  Node;
do_path([Head|Rest],Graph, Node) ->
  do_path(Rest, Graph, find_exit(Node, Head, Graph)).

run_path(Graph, Path, Start) ->
  do_path(Path, Graph, Start).


do_stuff(Graph) ->
  [ run_path(Graph, "LURLLLLLDUULRDDDRLRDDDUDDUULLRLULRURLRRDULUUURDUURLRDRRURUURUDDRDLRRLDDDDLLDURLDUUUDRDDDLULLDDLRLRRRLDLDDDDDLUUUDLUULRDUDLDRRRUDUDDRULURULDRUDLDUUUDLUDURUURRUUDRLDURRULURRURUUDDLRLDDDDRDRLDDLURLRDDLUDRLLRURRURRRURURRLLRLDRDLULLUDLUDRURDLRDUUDDUUDRLUDDLRLUDLLURDRUDDLRURDULLLUDDURULDRLUDLUDLULRRUUDDLDRLLUULDDURLURRRRUUDRUDLLDRUDLRRDUDUUURRULLDLDDRLUURLDUDDRLDRLDULDDURDLUUDRRLDRLLLRRRDLLLLURDLLLUDRUULUULLRLRDLULRLURLURRRDRLLDLDRLLRLULRDDDLUDDLLLRRLLLUURLDRULLDURDLULUDLRLDLUDURLLLURUUUDRRRULRDURLLURRLDLRLDLDRRUUDRDDDDDRDUUDULUL", $5),
  run_path(Graph, "RRURLURRULLUDUULUUURURULLDLRLRRULRUDUDDLLLRRRRLRUDUUUUDULUDRULDDUDLURLRRLLDLURLRDLDUULRDLLLDLLULLURLLURURULUDLDUDLUULDDLDRLRRUURRRLLRRLRULRRLDLDLRDULDLLDRRULRDRDUDUUUDUUDDRUUUDDLRDULLULDULUUUDDUULRLDLRLUUUUURDLULDLUUUULLLLRRRLDLLDLUDDULRULLRDURDRDRRRDDDLRDDULDLURLDLUDRRLDDDLULLRULDRULRURDURRUDUUULDRLRRUDDLULDLUULULRDRDULLLDULULDUDLDRLLLRLRURUDLUDDDURDUDDDULDRLUDRDRDRLRDDDDRLDRULLURUDRLLUDRLDDDLRLRDLDDUULRUDRLUULRULRLDLRLLULLUDULRLDRURDD", $3),
  run_path(Graph, "UUUUUURRDLLRUDUDURLRDDDURRRRULRLRUURLLLUULRUDLLRUUDURURUDRDLDLDRDUDUDRLUUDUUUDDURRRDRUDDUURDLRDRLDRRULULLLUDRDLLUULURULRULDRDRRLURULLDURUURDDRDLLDDDDULDULUULLRULRLDURLDDLULRLRRRLLURRLDLLULLDULRULLDLRULDDLUDDDLDDURUUUURDLLRURDURDUUDRULDUULLUUULLULLURLRDRLLRULLLLRRRRULDRULLUURLDRLRRDLDDRLRDURDRRDDDRRUDRLUULLLULRDDLDRRLRUDLRRLDULULRRDDURULLRULDUDRLRUUUULURLRLRDDDUUDDULLULLDDUDRLRDDRDRLDUURLRUULUULDUDDURDDLLLURUULLRDLRRDRDDDUDDRDLRRDDUURDUULUDDDDUUDDLULLDRDDLULLUDLDDURRULDUDRRUURRDLRLLDDRRLUUUDDUUDUDDDDDDDLULURRUULURLLUURUDUDDULURDDLRDDRRULLLDRRDLURURLRRRDDLDUUDR", $C)
].








