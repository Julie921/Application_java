# Application_java

Auteurs :
- Julie HALBOUT
- Delphine NGUYEN-DURANDET

Fake Quizlet est une application qui répond aux besoins suivants : 

- Les élèves peuvent faire des exercices de langues
- Les exercices sont corrigés et notés automatiquement par le système
- Les professeurs peuvent ajouter leurs propres exercices
- Les élèves peuvent consulter leurs progrès dans leurs langues
- Les professeurs peuvent consulter les progrès de leurs élèves

# Explication plus détaillée

## Les acteurs humains

Il existe deux types d'utilisateurs : les élèves et les professeurs. Les deux peuvent se connecter à l'application avec un pseudo unique.

### Les professeurs

Lors de la création du compte d'un professeur, il lui est demandé quelle langue il enseigne. Dans notre application, un professeur ne peut enseigner qu'une seule langue. Si le professeur entre la commande pour quitter l'application (`!quit`) alors qu'il est en train de s'inscrire, son compte n'est pas créé. 

Une fois que le professeur s'est identifié, trois actions sont possibles : 

1. Voir les exercices enregistrés : une preview (les trois premières phrases) de chaque exercice existant pour la langue qu'il enseigne est affichée, 
2. Voir les résultats de mes élèves : un tableau affiche le pseudo de chaque élève, son score dans la langue et son niveau correspondant, 
3. Ajouter un exercice : une fenêtre s'ouvre pour sélectionner son fichier d'exercice. Si ce dernier respecte bien les règles de formatage des exercices, il est ajouté au dossier "ressources" de l'application.

### Les élèves

Lors de la création de son compte, l'élève doit sélectionner les cours auxquels il veut s'inscrire. Il peut s'inscrire dans plusieurs langues. Par contre, il ne peut s'inscrire que dans un seul cours d'une même langue. Par exemple, l'élève peut être inscrit en cours de japonais avec Monsieur $X$ et en cours d'allemand avec Madame $Y$, mais il ne pourra pas s'inscrire en cours d'allemand avec Monsieur $B$ car il est déjà inscrit au cours de Madame $Y$ dans cette langue. 

Si l'utilisateur entre la commande pour quitter l'application (`!quit`), son compte est tout de même créé mais il n'est inscrit dans aucun cours. Il peut s'inscrire dans des cours ultérieurement.

Une fois inscrit et identifié, trois actions sont possibles : 

1. Faire un exercice : 
    1. Une preview de chaque exercice accessible pour l'élève s'affiche. Un exercice est considéré comme accessible pour l'élève si l'élève est inscrit dans la langue correspondant à celle de l'exercice et si le niveau de l'exercice correspond au niveau de l'élève dans la langue. Par exemple, si l'élève est DEBUTANT en espagnol, AVANCE en anglais et 












# Pour ajouter des choses
