job('Java Maven App DSL') {
    description('Java Maven App con DSL para el curso de Jenkins')
    scm {
        git('https://github.com/gpalacios26/java-app-jenkins.git', 'master') { node ->
            node / gitConfigName('gpalacios26')
            node / gitConfigEmail('gregpalacios26@gmail.com')
        }
    }
    steps {
        maven {
          mavenInstallation('mavenjenkins')
          goals('-B -DskipTests clean package')
        }
        maven {
          mavenInstallation('mavenjenkins')
          goals('test')
        }
        shell('''
          echo "Entrega: Desplegando la aplicación" 
          java -jar "/var/jenkins_home/workspace/Java Maven App DSL/target/my-app-1.0-SNAPSHOT.jar"
        ''')  
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
		mailer('gregpalacios26@gmail.com', true, true)
    }
}
