-module(qsort).
-export([qsort/1]).

qsort([]) -> [];
qsort([P|T]) -> qsort([X || X <-T, X < P]) ++ [P] ++ qsort([X || X <- T, X >= P]).

%% qsort([3,2,1]). => qsort([2,1]) ++ [3] ++ qsort([]).
