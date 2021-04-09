pipeline {

  agent any
  
    environment {
    branch = "${BRANCH}"
  }


  stages {
    stage("Terminate old containers") {
      steps {
        sh 'docker-compose down || true'
      }
    }

    stage("Test application") {
      steps {
        build job: 'auction-test', parameters: [string(name: 'BRANCH', value: branch)]
      }
    }


    stage("Build frontend and backend") {
      steps {
        build job: 'auction-frontend', parameters: [string(name: 'BRANCH', value: branch)]
        build job: 'auction-backend', parameters: [string(name: 'BRANCH', value: branch)]
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
