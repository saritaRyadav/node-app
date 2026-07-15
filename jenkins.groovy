pipeline {
    agent any

    stages {
        stage('System Info') {
            steps {
                sh 'pwd'
                sh 'whoami'
                sh 'hostname'
                sh 'date'
            }
        }
    }
}


----


pipeline {
    agent any

    parameters {
        string(name: 'NAME', defaultValue: 'John', description: 'Enter your name')
        choice(name: 'ENV', choices: ['Dev', 'QA', 'Prod'], description: 'Select environment')
        booleanParam(name: 'DEPLOY', defaultValue: false, description: 'Deploy application?')
    }

    stages {
        stage('Display Parameters') {
            steps {
                echo "Name: ${params.NAME}"
                echo "Environment: ${params.ENV}"
                echo "Deploy: ${params.DEPLOY}"
            }
        }
    }
}


----


pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/username/repository.git'
            }
        }

        stage('List Files') {
            steps {
                sh 'pwd'
                sh 'ls -la'
            }
        }
    }
}

---

pipeline {
    agent any


    stages {
        stage('Clone Tomcat Source') {
            steps {
                git branch: 'master', url: 'https://github.com/mayurmwagh/onlinebookstore.git'
            }
        }

        stage('Build Tomcat') {
            steps {
                mvn clean verify sonar:sonar \
  -Dsonar.projectKey=sonar2 \
  -Dsonar.projectName='sonar2' \
  -Dsonar.host.url=http://13.204.252.104:9000 \
  -Dsonar.token=sqp_9b18f5e3651f26f44d488ab55beab6bce8b72ee9 
            }
        }
    }
}


pipeline {
    agent any


    stages {
        stage('Clone Tomcat Source') {
            steps {
                git branch: 'master', url: 'https://github.com/mayurmwagh/onlinebookstore.git'
            }
        }

        stage('Build Tomcat') {
            steps {
                sh 'mvn clean package'
            }


        stage('test') {
            steps {
                mvn clean verify sonar:sonar \
  -Dsonar.projectKey=sonar2 \
  -Dsonar.projectName='sonar2' \
  -Dsonar.host.url=http://13.204.252.104:9000 \
  -Dsonar.token=sqp_9b18f5e3651f26f44d488ab55beab6bce8b72ee9 
            }

    }
}
 
 ---

pipeline {
    agent any

    stages {

        stage('Clone Source') {
            steps {
                git branch: 'master', url: 'https://github.com/mayurmwagh/onlinebookstore.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test & SonarQube Analysis') {
            steps {
                sh '''
                    mvn clean verify sonar:sonar \
  -Dsonar.projectKey=sonar2 \
  -Dsonar.projectName='sonar2' \
  -Dsonar.host.url=http://13.204.252.104:9000 \
  -Dsonar.token=sqp_b90cb303553a1632695c095f42dffa59e34a95f3
                '''
            }
        }

    }
}