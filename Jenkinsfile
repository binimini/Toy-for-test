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
    stage('publish'){
        sshPublisher(publishers:
        [sshPublisherDesc(configName: 'deploy-server', transfers:
        [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'ls', execTimeout: 120000, flatten: false, makeEmptyDirs: false,
        noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/dev', remoteDirectorySDF: false, removePrefix: 'build/libs', sourceFiles: 'build/libs/*.jar')]
        ,usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
    }
}
