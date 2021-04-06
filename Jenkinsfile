pipeline {

  agent any

  stages {
    stage("Terminate old containers") {
      steps {
        sh 'docker-compose down || true'
      }
    }

    stage("Test application") {
      steps {
        build job: 'auction-test'
      }
    }


    stage("Build frontend and backend") {
      steps {
        build job: 'auction-frontend'
        build job: 'auction-backend'
      }
    }

    stage("Deploy with docker-compose") {
      steps {
        sh 'docker-compose up -d'
      }
    }
  }

  post {
    always {
      cleanWs()
    }
  }
}
