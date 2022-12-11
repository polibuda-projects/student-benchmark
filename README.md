<<<<<<< HEAD
<h1 align="center">Student Benchmark - Backend-experiment Branch</h1>
 <br/>

 ### Requirements:
 - [Spring](https://spring.io)

 ## 1. Committing changes

 ### Make sure you are on `backend-experiments` branch
 ```bash
 git branch
 git checkout backend-experiments
=======
<p align="center">
   <a href="https://github.com/polibuda-projects/student-benchmark">
     <img alt="Student Benchmark" src="https://github.com/polibuda-projects/student-benchmark/blob/5ac20aa6809c7d77e059a8fea130a61e60efe6d6/frontend/public/img/logo.svg" width="220"/>
   </a>
 </p>


 <h1 align="center">Student Benchmark - Frontend Branch</h1>
 <br/>

 ## 1. Setting up development environment

 ### Requirements:
 - [Node.js](https://nodejs.org/en/)

 ### Setup

 1. Make sure that you have `Node.js` installed.
 2. Clone the repository
 ```bash
 git clone https://github.com/polibuda-projects/student-benchmark.git
 ```
 3. **Change branch to `frontend`**
 ```bash
 git checkout frontend
 ```

 4. Change working directory to `frontend`
 ```bash
 cd frontend
 ```
 5. Install dependencies
 ```bash
 npm ci
 ```
 6. Start development server
 ```bash
 npm start
 ```
 7. Open `http://localhost:3000` in your browser

 ## 2. Committing changes

 ### Make sure you are on `frontend` branch
 ```bash
 git branch
 git checkout frontend
>>>>>>> master
 ```

 ### Pulling from the repository
 ```bash
 git pull
 ```

 ### Adding files
 ```bash
 git add <file>
 ```

 ### Checking status of files
 ```bash
 git status
 ```

 ### Committing files
 ```bash
 git commit -m "<commit message>"
 ```

 ### Pushing to the repository
 ```bash
 git push origin
 ```

<<<<<<< HEAD
 ## 2. Commit naming conventions
 - Backend commits should start with `b` (e.g. `b added registration`)
 - Commits should be written in past tense (e.g. `b added registration` instead of `b add registration`)
 - If applicable, commit message should include name of the Trello card (e.g. `b added registration <trello card name>`)

=======
 ## 3. Commit naming conventions
 - Frontend commits should start with `f` (e.g. `f added login page`)
 - Commits should be written in past tense (e.g. `f added login page` instead of `f add login page`)
 - If applicable, commit message should include name of the Trello card (e.g. `f added login page <trello card name>`)

 ## 4. Frontend environment with backend
 
 ### Requirements:
  - [Node.js](https://nodejs.org/en/)
  - [Docker](https://www.docker.com/)
  
 ### Setup
 1. Copy .env.template to .env
 2. Set values in .env file (MAIL_* values are optional)
 
 ### Running with **frontend in development mode**
  1. Start docker containers in root directory
  ```bash
  docker compose -f docker-compose.dev.yml up
  ```
  ### Running with **frontend in production mode**
  1. Start docker containers in root directory
  ```bash
  docker compose up
  ```
  
>>>>>>> master
 ## Important notes

 - ### Pull changes before pushing
 - ### Do not commit to the `main` branch
<<<<<<< HEAD
 - ### Make sure that you are on `backend-experiments` branch
=======
 - ### Make sure that you are on `frontend` branch
 - ### You can commit directly from `VS Code`
 - ### Make sure  you are running `npm` commands from `frontend` directory

 ## Recommended VS Code extensions
 - [CSS Modules](https://marketplace.visualstudio.com/items?itemName=clinyong.vscode-css-modules)
 - [ESLint](https://marketplace.visualstudio.com/items?itemName=dbaeumer.vscode-eslint)
 - [OpenAPI (Swagger) Editor](https://marketplace.visualstudio.com/items?itemName=42Crunch.vscode-openapi)
 
 <h1 align="center">Hello Student Benchmark</h1>
 <br/>

 <h2 align="center">Project is still under development</h2>
>>>>>>> master
