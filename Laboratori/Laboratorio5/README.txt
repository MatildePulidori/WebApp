ISTRUZIONI

1) Avvio:

Lanciare il comando: npm install
Su un terminale serapareto lanciare il comando: json-server-auth db.json
Lanciare il comando: npm start
Aprire il browser all'indirizzo: http://localhost:4200/

2) Verifica delle funzionalità:

All'apertura viene mostrata la home di un teacher: a sinistra la sidebar mostra i corsi del docente (Applicazioni Internet, Programmazione di Sistema).

Scegliendo 'Applicazioni Internet', appare una sidenav, che mostra le sezioni: Studenti, Gruppi, VirtualMachines.

Cliccando su 'Studenti', verificare che, non essendo ancora stata fatta l'autenticazione, appare una finestra per fare login (l'URL deve essere "?doLogin=true").

Autenticarsi inserendo le credenziali email 'olivier@mail.com'  e password 'bestPassw0rd' e premere il bottone Login.

Il pulsante Login in alto a destra e` diventato Logout e il contenuto della pagina principale mostra la tabella implementata nel Laboratorio 4.

Premendo Logout si tornerà` non autenticati, si viene rediretti alla homepage http://localhost:4200/home.