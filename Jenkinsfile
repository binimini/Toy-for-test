node {
    stage ('clone') {
        git branch: 'main', credentialsId: 'localhost-credential', poll: false, url: 'https://github.com/binimini/Toy-for-test'
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
        sh 'chmod +x gradlew'
        sh './gradlew bootjar'
    }
    stage('publish'){
        sshPublisher(publishers:
        [sshPublisherDesc(configName: 'deploy-server', transfers:
        [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'ls', execTimeout: 120000, flatten: false, makeEmptyDirs: false,
        noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/dev', remoteDirectorySDF: false, removePrefix: 'build/libs', sourceFiles: 'build/libs/toy-0.0.1-SNAPSHOT.jar')]
        ,usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
    }
}
