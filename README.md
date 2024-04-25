# Horse Race Simulator

## Project Description
A java program which simulates a horse race. Create and customize your own horse, buy accessories and try your luck with an advanced horse betting algorithm!

## Project Information

### Saved Data

Users balance can manually be modified in balance.txt

When the program is restarted, some of the horses from the previous race will be reused, but new horses may also be created to prevent repetitiveness. Horses and statistics can be manually modified in horse_stats.txt : they are written in format 'wins : losses : [recentRanks]'

### Interface

You can access gambling part after running a single race. Use gambling interface to view horse statistics.

Average rank is calulcated by getting an average of up to 5 recent races



### Confidence

Information point: by default, on each turn horse has a chance to fall ([confidence ^ 2] * 0.1). When a horse falls, its confidence drops by 0.01, when a horse wins, its confidence increases by 0.05.

Information point: Each item adds a chance a 5% chance that a horse moves an extra step during its turn. Sunny condition is a standard condition, Rainy condition increases falling probability of a horse by 1% on each turn. Icy condition increases a fall probability by 2% on each turn but adds a 40% to move an additonal space.


### Odds calculation: 

for non-zero winrate horses: (10 - winRate * averagePlacement / 10.0)

for zero winrate horses: 10

## How to install and run:
-Ensure your java is up to date
-Run the main method in startRaceGUI
