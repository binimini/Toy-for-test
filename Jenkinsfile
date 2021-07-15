node {
    stage ('clone') {
        git 'https://github.com/binimini/Toy-for-test.git' // git clone
    }
    stage('config'){
        dir('src/main/resources/'){
            configFileProvider([configFile(fileId: 'token_properties', variable: 'TOKEN')]) {
                    def contents = readFile file: "$TOKEN"
                    writeFile file: 'application.properties', text: contents
                }
        }
    }
    stage('build'){
        sh './gradlew bootjar'
    }
    stage('docker build'){
        def app = docker.build("binimini/toy-discord")
    }
    stage('docker push') {
        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credential')
        app.push("latest")
    }
}
