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
}
