<p align="center">
   <a href="https://github.com/polibuda-projects/student-benchmark">
     <img alt="Student Benchmark" src="https://github.com/polibuda-projects/student-benchmark/blob/5ac20aa6809c7d77e059a8fea130a61e60efe6d6/frontend/public/img/logo.svg" width="220"/>
   </a>
 </p>

 <h1 align="center">Hello Student Benchmark</h1>
 <br/>

![react](https://img.shields.io/badge/react-18.2.0-9cf)
![chart](https://img.shields.io/badge/chart.js-4.0.1-red)
![ts](https://img.shields.io/badge/typescript-4.9.3-blue)
![node.js](https://img.shields.io/badge/node.js-18.2.1-green)
![java](https://img.shields.io/badge/java-17-important)
![springboot](https://img.shields.io/badge/spring_boot-2.6.13-green)
![build](https://img.shields.io/badge/build-passed-success)
![tests](https://img.shields.io/badge/tests-passed-success)

 <h1 align="center">Challenge your memory and cognitive skills.</h1>
 
## You can challenge your memory with 4 tests:

### `Sequence memory`
Memorize the sequence of buttons that light up, then press them in order. Every time you finish the pattern, it gets longer. Make a mistake, and the test is over.

### `Visual memory` 
Every level, a number of tiles will flash white. Memorize them, and pick them again after the tiles are reset! Levels get progressively more difficult, to challenge your skills. You have three lives. Make it as far as you can!

### `Verbal memory`
This test measures how many words you can keep in short term memory at once. Go as long as you can. You have 3 strikes until game over. Your score is how many turns you lasted.

### `Number memory` 
Remember the longest number you can. The average person can only remember 7 digit numbers reliably, but it's possible to do much better using mnemonic techniques.

## Compete with each other!
You can compere your score with all users on graph. Check the best results on dashboard.


## Setting up development environment

 ### Requirements:
 - [Node.js](https://nodejs.org/en/)
 - [Docker](https://www.docker.com/)

 ### Setup

 1. Make sure that you have `Node.js` installed.
 2. Clone the repository
 ```bash
 git clone https://github.com/polibuda-projects/student-benchmark.git
 ```
 3. Copy .env.template to .env
 4. Set values in .env file (MAIL_* values are optional)
 
### Running in production mode
1. Start docker containers in root directory
```bash
docker compose up
```
2. Open `http://localhost:3000` in your browser  
