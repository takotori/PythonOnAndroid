# PythonOnAndroid :snake:
In unserer App implementieren wir das Spiel Snake. Der Spieler soll dabei die Schlange mittels 
Bewegungssensoren steuern können. Es gibt eine Offline- sowie eine Online-Modus. 
Um online spielen zu können muss der Nutzer sich mit einem Google-Account anmelden. 
Die Spielstände werden im Online Modus auf Google Firebase gespeichert 
und offline in der Datenbank geschrieben.

## Punkte Auflistung
* Verwendung von Kotlin statt Java           **(6 Punkte)**
* Persistenz / Offlinefähigkeit         **(min. 1 Punkt)**
* Verwendung von Webservices            **(min. 1 Punkt)**
* Verwendung von Aktoren oder Sensoren  **(min. 1 Punkt)**
* Verwendung von Jetpack-Komponenten    **(min. 1 Punkt)**
* Integration von Drittkomponenten      **(min. 1 Punkt)**
* Ressourcen: Unterschiedliche Styles   **(min. 1 Punkt)**
* Funktionalität pro Screen **(OptionActivity, GameActivity, ScoreActivity und MenuActivity plus LogginActivity min. 3 Punkte)**
* Total Punkte min: **15 Punkte**

## Screenshots

### LoginActivity wen Internet vorhanden mit Firebase-Authentication
<img src="https://user-images.githubusercontent.com/114221850/206316586-3758d949-da6d-4c8c-a0bf-73149b2a21f7.jpg" height="400" width="auto">

### MenuActivity mit dunkel und hellem Theme
<img src="https://user-images.githubusercontent.com/114221850/206316239-9c64a0b6-55c8-4f8f-b4bb-c174ed2740ff.jpg" height="400" width="auto"> <img src="https://user-images.githubusercontent.com/114221850/206316587-0ad9ab36-3ec3-486c-9646-569ced915f60.jpg" height="400" width="auto">

#### MenuActivity offline Gamestart
<img src="https://user-images.githubusercontent.com/114221850/206316574-e7804c02-1011-4e3c-9ab6-6b095228fa6e.jpg" height="400" width="auto">

### OptionsActivity
<img src="https://user-images.githubusercontent.com/114221850/206316564-d52fcd7b-36a9-43b3-b090-2ddb18e43b8c.jpg" height="400" width="auto"><img src="https://user-images.githubusercontent.com/114221850/206316582-94bc9917-a11d-4665-97a1-69671a92f1e9.jpg" height="400" width="auto">

#### Colorpicker in OptionsActivity
(Drittkomponente: https://github.com/Dhaval2404/ColorPicker) 
<img src="https://user-images.githubusercontent.com/114221850/206316585-4b90d67d-19b3-4fa3-bcf2-16caeae7cd25.jpg" height="400" width="auto">

### ScoreActivity
Wen Internet vorhanden dan kommen die Daten aus der Firebase Realtime Database und sonst von der SQLite-DB via Room
<img src="https://user-images.githubusercontent.com/114221850/206316570-395344b7-87f0-4dee-9149-a9020df52f84.jpg" height="400" width="auto">

### GameActivity
<img src="https://user-images.githubusercontent.com/114221850/206316579-224637f4-775e-40e4-8b36-2b1da41c8b2e.jpg" height="400" width="auto"><img src="https://user-images.githubusercontent.com/114221850/206316588-26c2fb2c-cb42-4ff8-b51a-6ede972e8d40.jpg" height="400" width="auto">

#### GameActivity Game Over
<img src="https://user-images.githubusercontent.com/114221850/206316581-f41a2c5b-1881-435e-bab5-2f824dc416ba.jpg" height="400" width="auto">
