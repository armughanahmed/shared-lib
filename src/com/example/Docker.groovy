#!/usr/bin/env groovy
package com.example

class Docker implements Serializable {

    def script

    Docker(script) {
        this.script = script
    }

    def buildDockerImage(String imageName) {
        script.echo "building the docker image..."
        // script.sh "docker build -t $imageName ."
        script.withCredentials([script.usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        script.docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
            script.docker.build("$script.USER/$imageName", '.').push()
        }
    }

    // def dockerLogin() {
    //     script.withCredentials([script.usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
    //         script.sh "echo $script.PASS | docker login -u $script.USER --password-stdin"
    //     }
    // }

    def dockerPush(String imageName) {
        script.sh "docker push $imageName"
    }

}
