pipeline {
    agent any

        environment {
            IMAGE_NAME = "bank-app:latest"
        }

        stages {
            stage('Checkout') {
                steps {
                    git branch: 'main', url: 'https://github.com/RafalStasiowski/bank-api.git'
                }
            }
            stage('Build') {
                steps {
                    sh './gradlew clean bootJar'
                }
            }   
            stage('Test') {
                steps {
                    sh './gradlew test'
                }
            }
            stage('Docker Build') {
                steps {
                    sh "docker build -t ${IMAGE_NAME} ."
                }
            }
            stage('Deploy') {
                steps {
                    sshagent (credentials: ['vps-ssh-credentials']) {
                        sh "ssh user@VPS_IP 'cd /path/to/deployment && docker-compose pull && docker-compose up -d'"
                    }
                }
            }
        }
        post {
            failure {
                echo "Pipeline failed!"
            }
        }
}