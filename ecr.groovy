pipeline {
    agent any
    environment {
        AWS_REGION     = "ap-south-1"   // Replace with your AWS Region
        AWS_ACCOUNT_ID = "884404875406"   // Replace with your AWS Account ID
        ECR_REPO       = "testing"
        IMAGE_TAG      = "1.0"
    }
    stages{
        stage('CODE_PULL'){
            steps{
                git branch: 'master', 
                     url: 'https://github.com/mayurmwagh/onlinebookstore.git'   
            }
        }
        stage('Build'){
            steps{
                sh 'mvn clean package'
            }
        }
        stage('Aws_login'){
            steps{
                withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'ecr', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]) {
 
                  sh 'aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com'
                } 
            }
            
        }
        stage ('Docker-Build'){
            steps{
                sh 'docker build -t ${ECR_REPO}:${IMAGE_TAG} .'
            }
        }
        stage('docker-tag'){
            steps{
                sh 'docker tag ${ECR_REPO}:${IMAGE_TAG} ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO}:${BUILD_ID}'

            }
        }
        stage('docker-push'){
            steps{
                sh 'docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO}:${BUILD_ID}'
            }
        }
    }
}