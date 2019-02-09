pipeline {
    agent any

    stages {
        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('JaCoCo') {
            steps {
                jacoco()
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        stage('Install') {
            steps {
                sh 'mvn install -U -DskipTests'
            }
        }
    }
}
