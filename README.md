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

Pour lancer l'application : 

```bash
cd build/release
java -jar Application_java.jar
```

Pour quitter l'application, il faut rentrer le mot clé `!quit`.

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

L'élève est attribué automatiquement le score de 0 et le niveau DEBUTANT dans la langue. 

Une fois inscrit et identifié, trois actions sont possibles : 

1. Faire un exercice : 
    1. Une preview de chaque exercice accessible pour l'élève s'affiche. Un exercice est considéré comme accessible pour l'élève si l'élève est inscrit dans la langue correspondant à celle de l'exercice et si le niveau de l'exercice correspond au niveau de l'élève dans la langue ou à un niveau inférieur à l'élève. Par exemple, si l'élève est DEBUTANT en espagnol et AVANCE en anglais, il s'affichera une preview de tous les exercices en espagnol pour DEBUTANT et tous les exercices en anglais pour DEBUTANT, INTERMEDIAIRE et AVANCE.
   2. L'élève sélectionne l'exercice qu'il veut faire.
   3. (Pour l'instant, seulement les exercices à trous sont disponibles) L'élève répond aux questions ce qui construit un objet de la classe ReponseEleveExoATrous.
   4. Cet objet permet également d'attribuer une note à l'élève, de lui afficher les endroits où il a eu bon ou faux et de lui dire si l'exercice est considéré comme réussi ou non (selon s'il dépasse la note pallier fixé par le prof qui a créé l'exercice).
   5. Il lui est proposé de voir ou non les réponses correctes.
2. Voir le notes qu'il a obtenues : un tableau s'affiche avec pour chaque langue qu'il étudie le nom de son professeur, son score et son niveau.
3. S'inscrire dans un nouveau cours : si l'élève choisit un cours d'une langue dans laquelle il est déjà inscrit, il ne peut pas s'inscire. S'il choisit un cours d'une langue dans laquelle il n'est pas déjà inscrit, un message indique que l'inscription est réussie. 

## Les règles de gestion

### Le passage d'un niveau 

Il existe quatre niveaux : DEBUTANT, INTERMEDIAIRE, AVANCE, EXPERT. Pour qu'un élève passe d'un niveau à un autre, il faut que son score soit compris dans une certaine plage. 

- DEBUTANT : en-dessous de 20
- INTERMEDIAIRE : entre 20 et 40
- AVANCE : entre 40 et 60
- EXPERT : au-dessous de 60

Le passage d'un niveau inférieur à un niveau supérieur est donc possible, *tout comme* la régression : un élève peut passer d'un niveau supérieur à un niveau inférieur si son score descend trop.

L'élève a accès à tous les exercices de son niveau et des niveaux inférieurs au sien. 

Pour que son score augmente, il faut que l'élève fasse un exercice et que l'exercice soit considéré comme réussi. Un exercice est considéré comme réussi si la note obtenue dépasse la note seuil fixée avec les métadonnées fournies par le professeur lors de la création de l'exercice. En effet, quand le prof crée un exercice, il doit renseigner le pourcentage de points qu'il faut obtenir pour que l'exercice soit considéré comme réussi. La note seuil est calculée à partir de ce pourcentage et du nombre de réponses à fournir en tout[^note_seuil].

[^note_seuil]: Si l'exercice a 10 réponses à fournir et que le pourcentage est de 50%, il faudra obtenir 5 points. S'il avait été de 75%, il aurait fallu obtenir 7.5 points. 

Nous avons choisi de fonctionner en pourcentage plutôt que de demander au prof de renseigner le nombre de points directement parce que c'est plus facile à gérer pour un prof si l'exercice est très long. Les pourcentages permettent également de créer des exercices plus ou moins difficiles pour un même niveau. 

Si l'élève réussit l'exercice, son score est incrémenté de 1. Sinon, son score est décrémenté de 1. 

Selon son niveau, l'élève n'est pas noté de la même manière (classe `BaremeNiveau`) : 

- DEBUTANT : 
  - +1 par bonne réponse
  - 0 par mauvaise réponse
  - 0 par réponse non-répondue
- INTERMEDIAIRE
   - +1 par bonne réponse
   - -1 par mauvaise réponse
   - 0 par réponse non-répondue
- AVANCE 
   - +1 par bonne réponse
   - -1 par mauvaise réponse
   - -1 par réponse non-répondue
- EXPERT
   - +1 par bonne réponse
   - -2 par mauvaise réponse
   - -1 par réponse non-répondue

### La correction des exercices

La correction se fait automatiquement. L'objet de la classe `ReponseEleve`[^classe_abstraite] est construit lorsque l'élève répond à un exercice. Dans le constructeur, une fois toutes les réponses de l'élève récupéré, l'attribut `reponsesCorrection` est instancié grâce à la méthode `corrige()` (interface `Correction`). Cette méthode attribue une `ValeurReponse` (énumération) à chaque réponse. Chaque réponse peut être soit "VRAI", soit "FAUX", soit "NR" (non-répondue)

[^classe_abstraite]: `ReponseEleve` est une classe abstraite. La classe `ReponseEleveExoATrous` est le classe concrète qui étend cette classe et qui est spécifique aux exercices à trous.

Dans l'interface du terminal s'affiche alors l'exercice reconstitué avec les réponses de l'élève (grâce à la méthode `affichePhrasesRempliesAvecCouleurs()`). Si l'élève a eu bon, son mot est surligné en vert, si l'élève a eu faux, en rouge et s'il n'a pas répondu, "___" s'affichent en jaune.

L'élève choisit de voir ou non les vraies réponses correctes qu'il fallait fournir.

## Ajout d'un exercice

Si un professeur veut ajouter un exercice, il faut que son fichier `.txt` soit formaté correctement. Sur la première ligne de son fichier doivent apparaître les informations suivantes séparées par des ":" : 

- La langue de l'exercice : DE, EN, ES, FR, JPN, ZH (énumération `Langue`)
- Le type d'exercice : EXO_A_TROU, EXO_TERMINAISON (énumération `TypeExo`)
- Le niveau de l'exercice : DEBUTANT, INTERMEDIAIRE, AVANCE, EXPERT (énumération `BaremeNiveau`)
- Le pourcentage de points à obtenir pour que l'exercice soit considéré comme réussi : entre 0 et 1.

Par exemple, si je veux créer un exercice à trous en français pour les débutants et que je veux que mes élèves aient la moitié des points, je dois écrire : 

```plain
FR:EXO_A_TROU:DEBUTANT:0.5
```

La méthode `readFromFile()` de la classe `ImportExercice` permet de lire le fichier et de créer l'objet `Exercice` correspondant. Le fichier est également copié dans le dossier `ressources` de l'application pour que l'exercice puisse être accessible même après la fermeture de l'application.

A noter que peu importe quel prof a créé l'exercice, il sera accessible par tout le monde, pas seulement aux étudiants qui sont inscrits au cours de ce professeur.

### Et si quelqu'un veut créer un nouveau type d'exercice ? 

Pour créer un nouveau type d'exercice et l'implémenter dans cette application, il faut : 

- Ajouter le type dans l'énumération `TypeExo`
- Créer une classe qui étend la classe `Exercice`
- Créer une classe qui étend la classe `Metaparse` et qui permet de parser l'exercice à partir de l'input du prof
- Créer une classe qui étend la classe `ReponseEleve` et qui permet de récupérer les réponses de l'élèves, de le noter, de dire s'il valide ou non et d'afficher l'exercice reconstitué avec ses réponses
- Dans la classe `ImportExercice`, ajouter un `case` pour notre type d'exercice afin que l'objet soit créé avec la bonne classe concrète lors de l'import d'un exercice depuis un fichier `.txt`.

## Database

Nous avons choisi de stocker les informations de l'application avec une base de données H2. Nous avons utilisé ORMlite pour gérer la BDD. Les informations stockées sont : 

- Les professeurs (pseudo et langue),
- Les élèves (pseudo),
- Les `NiveauxEleves` : cette table renseigne, pour chaque élève et pour chaque langue qu'il étudie son prof, son niveau, son score. 

Quand l'application est ouverte, la fonction `createFromDatabase()` du `Main` permet de recréer les objets à partir des informations gardées dans la DataBase pour qu'on puisse utiliser les méthodes des objets. 

A noter que les exercices ne sont pas stockés dans la DataBase mais dans le dossier `ressources` de l'application. A l'ouverture de l'application, les objets `Exercice` sont recréés grâce à la méthode `importDossier()` de la classe `ImportExercice`.

# Demo

Pour accéder à la javadoc : `build/release/javadoc/index.html` et la diagramme des classes est disponible dans `build/release/package_diagram.svg`.

Afin de pouvoir tester l'application, nous avons créé une base de données fictive avec les informations suivantes : 

- Table des élèves : 

| PSEUDO        |
|---------------|
| alice_du98    |
| benjamin2003  |
| camille_du95  |
| chloe_du97    |
| juliette_du96 |

- Tables des professeurs : 

| PSEUDO         | LANGUE |
|----------------|--------|
| JeanDurand     | FR     |
| SophieGarcia   | FR     |
| FrancoisDupont | DE     |
| LucieBertrand  | DE     |
| NicolasPerrin  | DE     |
| VirginieRobert | JPN    |
| CharlesRenard  | ZH     |
| CedricMoreau   | ES     |
| ClementVincent | EN     |
| BenjaminDubois | ZH     |

- Table avec pour chaque langue étudiée par chaque élève son niveau, son prof et son score : 

| INDEX | PSEUDO_ELEVE  | LANGUE | NIVEAU        | PSEUDO_PROFESSEUR | SCORE |
|-------|---------------|--------|---------------|-------------------|-------|
| 1     | alice_du98    | FR     | INTERMEDIAIRE | JeanDurand        | 21    |
| 2     | alice_du98    | DE     | DEBUTANT      | LucieBertrand     | 11    |
| 3     | benjamin2003  | JPN    | EXPERT        | VirginieRobert    | 71    |
| 4     | camille_du95  | ZH     | AVANCE        | BenjaminDubois    | 46    |
| 5     | camille_du95  | EN     | INTERMEDIAIRE | ClementVincent    | 26    |
| 6     | chloe_du97    | ES     | DEBUTANT      | CedricMoreau      | 19    |
| 7     | juliette_du96 | FR     | DEBUTANT      | JeanDurand        | 10    |
  
Nous avons également créé quelques exercices : 

| Langue    | Niveau        | Nombre d'exo |
|-----------|----------------|---------------|
| DE        | DÉBUTANT       | 2             |
| DE        | INTERMÉDIAIRE  | 1             |
| FR        | DÉBUTANT       | 3             |
| FR        | INTERMÉDIAIRE  | 2             |
| FR        | AVANCÉ         | 1             |
| FR        | EXPERT         | 1             |
| JPN       | DÉBUTANT       | 1             |
| JPN       | INTERMÉDIAIRE  | 2             |
| ZH        | DÉBUTANT       | 2             |
| ZH        | AVANCÉ         | 1             |

