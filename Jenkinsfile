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
                        sh "ssh root@joanna163.mikrus.xyz 'cd /app && docker-compose pull && docker-compose up -d'"
                    }
                }
            }
        }
        post {
            always {
                sshagent (credentials: ['vps-ssh-credentials']) {
                    sh "ssh root@joanna163.mikrus.xyz 'cd /app && docker-compose down -v'"
                }
            }
            failure {
                echo "Pipeline failed!"
            }
        }
}