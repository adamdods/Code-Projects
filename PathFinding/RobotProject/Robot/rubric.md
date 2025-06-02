## (0) Print DFA
----------------------------

### Criteria

|Name        |Weight    |Description                       |
|------------|----------|----------------------------------|
|Errors      | 60.0% cap|Program executed with errors      |
|Compiled    | 10.0%    |Program compiled with no errors   |
|Format      | 27.0%    |Formatting of the table           |
|Paths       | 63.0%    |Correctness of the shortests paths|
### Results

|Test                |      Errors |    Compiled |      Format |       Paths |       Marks |
|--------------------|------------ |------------ |------------ |------------ |------------ |
|big                 |          No |      100.0% |        0.0% |        0.0% |       10.0% |
|example             |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_13              |          No |      100.0% |      100.0% |        0.0% |       99.8% |
|gen_14              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_8               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_1               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_6               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|trivial             |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_15              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_12              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_7               |          No |      100.0% |      100.0% |        0.0% |       96.2% |
|gen_9               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_5               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_2               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_17              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_10              |          No |      100.0% |        0.0% |        0.0% |       10.0% |
|gen_19              |          No |      100.0% |      100.0% |        0.0% |       98.4% |
|gen_3               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_4               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|forward             |          No |      100.0% |      100.0% |        0.0% |       97.9% |
|gen_20              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_18              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_11              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_16              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|pair                |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|**Total**           |             |             |             |             |   **92.5%** |
### Comments

 - big:
	 - Could not parse input: Infinite loop found.
 - gen_10:
	 - Could not parse input: Infinite loop found.
## (1) Print maze DFA
----------------------------

### Criteria

|Name        |Weight    |Description                       |
|------------|----------|----------------------------------|
|Errors      | 60.0% cap|Program executed with errors      |
|Compiled    | 10.0%    |Program compiled with no errors   |
|Format      | 27.0%    |Formatting of the table           |
|Paths       | 63.0%    |Correctness of the shortests paths|
### Results

|Test                |      Errors |    Compiled |      Format |       Paths |       Marks |
|--------------------|------------ |------------ |------------ |------------ |------------ |
|big                 |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|example             |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_13              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_14              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_8               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_1               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_6               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_15              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_12              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_7               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|small               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_9               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|wide                |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|tiny                |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_5               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_2               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_17              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_10              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_19              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_3               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_4               |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_20              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_18              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_11              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|tall                |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|gen_16              |          No |      100.0% |      100.0% |      100.0% |      100.0% |
|**Total**           |             |             |             |             |  **100.0%** |
## (2) Simon says
----------------------------

### Criteria

|Name        |Weight    |Description                       |
|------------|----------|----------------------------------|
|Errors      | 60.0% cap|Program executed with errors      |
|Compiled    | 10.0%    |Program compiled with no errors   |
|Format      |  9.0%    |Correct format and no extra moves |
|Targets     | 31.5%    |Number of targets reached         |
|Paths       | 49.5%    |Correctness of the shortests paths|
### Results

|Test                |      Errors |    Compiled |      Format |     Targets |       Paths |       Marks |
|--------------------|------------ |------------ |------------ |------------ |------------ |------------ |
|big                 |          No |      100.0% |        0.0% |        0.0% |        0.0% |       10.0% |
|example             |          No |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |
|trivial             |          No |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |
|forward             |          No |      100.0% |        0.0% |      100.0% |        0.0% |       45.1% |
|pair                |          No |      100.0% |        0.0% |      100.0% |      100.0% |       94.6% |
|**Total**           |             |             |             |             |             |   **69.9%** |
### Comments

 - big:
	 - Could not parse input: H
 - forward:
	 - Moved after finishing targets.
	 - Could not parse input
 - pair:
	 - Moved after finishing targets.
## (3) Solve maze
----------------------------

### Criteria

|Name        |Weight    |Description                       |
|------------|----------|----------------------------------|
|Errors      | 60.0% cap|Program executed with errors      |
|Compiled    | 10.0%    |Program compiled with no errors   |
|Format      |  9.0%    |Correct format and no extra moves |
|Targets     | 31.5%    |Number of targets reached         |
|Paths       | 49.5%    |Correctness of the shortests paths|
### Results

|Test                |      Errors |    Compiled |      Format |     Targets |       Paths |       Marks |
|--------------------|------------ |------------ |------------ |------------ |------------ |------------ |
|big                 |          No |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |
|example             |          No |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |
|small               |          No |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |
|wide                |          No |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |
|tiny                |          No |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |
|tall                |          No |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |
|**Total**           |             |             |             |             |             |  **100.0%** |
## (4) Traverse maze
----------------------------

### Criteria

|Name        |Weight    |Description                                    |
|------------|----------|-----------------------------------------------|
|Errors      | 60.0% cap|Program executed with errors                   |
|Compiled    |  6.7%    |Program compiled with no errors                |
|Format      |  6.0%    |Correct format and no extra moves              |
|Targets     | 21.1%    |Number of targets reached                      |
|DFA Paths   | 33.1%    |Correctness of the shortests paths for the dfa |
|Maze Paths  | 33.1%    |Correctness of the shortests paths for the maze|
### Results

|Test                |      Errors |    Compiled |      Format |     Targets |   DFA Paths |  Maze Paths |       Marks |
|--------------------|------------ |------------ |------------ |------------ |------------ |------------ |------------ |
|forward_tiny        |          No |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |
|forward_big         |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|big_tiny            |          No |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |
|forward_tall        |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|example             |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|trivial_small       |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|pair_small          |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|pair_wide           |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|big_tall            |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|trivial_wide        |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       23.6% |
|trivial_big         |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|big_small           |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|trivial_tiny        |          No |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |
|pair_big            |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|pair_tiny           |          No |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |      100.0% |
|big_big             |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|forward_small       |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|forward_wide        |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|pair_tall           |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|big_wide            |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|trivial_tall        |          No |      100.0% |      100.0% |        0.0% |        0.0% |        0.0% |       12.7% |
|**Total**           |             |             |             |             |             |             |   **29.9%** |
### Comments

 - forward_big:
	 - Reached 0/19 targets.
 - forward_tall:
	 - Reached 0/16 targets.
 - example:
	 - Reached 0/3 targets.
 - trivial_small:
	 - Reached 0/4 targets.
 - pair_small:
	 - Reached 0/12 targets.
 - pair_wide:
	 - Reached 0/5 targets.
 - big_tall:
	 - Reached 0/5 targets.
 - trivial_wide:
	 - Reached 2/16 targets.
 - trivial_big:
	 - Reached 0/11 targets.
 - big_small:
	 - Reached 0/15 targets.
 - pair_big:
	 - Reached 0/13 targets.
 - big_big:
	 - Reached 0/20 targets.
 - forward_small:
	 - Reached 0/12 targets.
 - forward_wide:
	 - Reached 0/12 targets.
 - pair_tall:
	 - Reached 0/14 targets.
 - big_wide:
	 - Reached 0/16 targets.
 - trivial_tall:
	 - Reached 0/10 targets.
