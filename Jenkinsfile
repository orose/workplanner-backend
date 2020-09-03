pipeline {
    agent any

    stages {
        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            environment {
                WORKPLANNER_TEST_DB = credentials('workplanner_test_db')
                SPRING_DATASOURCE_URL = "jdbc:postgresql://10.0.0.4:5432/test_jenkins_workplanner"
            }
            steps {
                sh "mvn test -Dspring.datasource.url=${env.SPRING_DATASOURCE_URL} -Dspring.datasource.username=${env.WORKPLANNER_TEST_DB_USR} -Dspring.datasource.password=${env.WORKPLANNER_TEST_DB_PSW}"
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
