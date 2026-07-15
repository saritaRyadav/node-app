pipeline {
    agent none

    stages {

        stage('Checkout') {
            agent any
            steps {
                git branch: 'main',
                    url: 'https://github.com/saritaRyadav/jenkins.git'
            }
        }

        stage('Build') {
            agent {
                docker {
                    image 'maven:3.9.11-eclipse-temurin-17'
                    reuseNode true
                }
            }

            steps {
                sh 'mvn clean package'
            }
        }
    }
}

---

pipeline {
    agent none

    stages {
        stage('Checkout') {
            agent any
            steps {
                git branch: 'main',
                    url: 'https://github.com/mayurmwagh/node-app.git'
            }
        }

        stage('Install Dependencies') {
            agent {
                docker {
                    image 'node:20'
                    reuseNode true
                    args '-u root'
                }
            }
            environment {
                HOME = "${WORKSPACE}"
                NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
            }
            steps {
                sh 'npm install'
            }
        }
    }
}



