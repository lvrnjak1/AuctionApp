pipeline {

  agent { label 'Jenkins_Slave' }

  stages {
    stage("Terminate old containers") {
      steps {
        sh 'docker-compose down || true'
      }
    }
    
    
    stage("Prepare frontend and build backend") {
      steps {
        build job: 'frontend'
        build job: 'backend'
      }
    }

    stage("Deploy with docker-compose") {
      steps {
        sh 'docker-compose up -d'
      }
    }
    # Run smoke tests after deployment stage
    # build job: 'smoke'
  }

  post {
    always {
      cleanWs()
    }
  }
}
