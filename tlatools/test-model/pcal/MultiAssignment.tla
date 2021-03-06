--------------------------- MODULE MultiAssignment -------------------------- 
(***************************************************************************)
(* A test of multi-assignment statements with arrays.                      *)
(***************************************************************************)

EXTENDS Naturals, TLC



(*****


--algorithm MultiAssignment
  process Proc \in 1..3
  variables A = [i \in 1..5 |-> i] ; x = 0 ;
  begin a : A[1] := A[3] ||  x := 7 || A[3] := A[1] ;
            assert <<3 , 1>> = <<A[1], A[3]>> ;
        b : assert <<3 , 1>> = <<A[1], A[3]>> ;
  end process
  end algorithm 

****)
					
(***** BEGIN TRANSLATION ***)
VARIABLES pc, A, x

vars == << pc, A, x >>

ProcSet == (1..3)

Init == (* Process Proc *)
        /\ A = [self \in 1..3 |-> [i \in 1..5 |-> i]]
        /\ x = [self \in 1..3 |-> 0]
        /\ pc = [self \in ProcSet |-> "a"]

a(self) == /\ pc[self] = "a"
           /\ /\ A' = [A EXCEPT ![self][1] = A[self][3],
                                ![self][3] = A[self][1]]
              /\ x' = [x EXCEPT ![self] = 7]
           /\ Assert(<<3 , 1>> = <<A'[self][1], A'[self][3]>>, 
                     "Failure of assertion at line 17, column 13.")
           /\ pc' = [pc EXCEPT ![self] = "b"]

b(self) == /\ pc[self] = "b"
           /\ Assert(<<3 , 1>> = <<A[self][1], A[self][3]>>, 
                     "Failure of assertion at line 18, column 13.")
           /\ pc' = [pc EXCEPT ![self] = "Done"]
           /\ UNCHANGED << A, x >>

Proc(self) == a(self) \/ b(self)

Next == (\E self \in 1..3: Proc(self))
           \/ (* Disjunct to prevent deadlock on termination *)
              ((\A self \in ProcSet: pc[self] = "Done") /\ UNCHANGED vars)

Spec == /\ Init /\ [][Next]_vars
        /\ \A self \in 1..3 : WF_vars(Proc(self))

Termination == <>(\A self \in ProcSet: pc[self] = "Done")

(***** END TRANSLATION ***)
=============================================================================
