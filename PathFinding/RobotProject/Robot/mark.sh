build_output=$(ant clean compile jar 2>&1)
build_error_found=$?
jar_path="../build/jar/application.jar"
test_path="testsuite/tests"
rubric_path="rubric.md"
mark_path="marks.txt"
tool_path="testsuite/tools"

function run() {

  case $1 in

    0)
      args="dfa.in"
      mode="0"
      ;;

    1)
      args="maze.in"
      mode="1"
      ;;

    2)
      args="targets.in dfa.in"
      mode="2"
      ;;

    3)
      args="targets.in maze.in"
      mode="3"
      ;;

    4)
      args="targets.in dfa.in maze.in"
      mode="4"
      ;;

    *)
      echo "Marking all modes"
      for mode in {0..4}; do
        run $mode
      done
      exit 0
      ;;
  esac

  echo "Testing on mode $mode".

  rm -rf ./out
  mkdir -p ./out
  cd out
  for dir in ../$test_path/$mode/$2*; do
      [ -e "$dir" ] || continue # Test if directory exists
      test=$(basename $dir)
      echo "Testing: $test"
      if [ $build_error_found -eq 1 ]; then
          echo "Compilation error: See error file for details." > "$test.out"
          echo "$build_output" > "$test.err"
      else
          cargs=""
          for arg in $args; do
              cargs="${cargs} $dir/$arg"
          done;
          out=$(java -jar $jar_path $mode $cargs 1> "$test.out" 2> "$test.err")
      fi
  done
  cd ..
  
  python3 $tool_path/mark.py $1 --test-path="$test_path" --rubric-path="$rubric_path" > /dev/null
}
rm -f "$rubric_path"
rm -f "$mark_path"
run "$1"
