# Progetti Java

Esercizi e progetti in linguaggio Java svolti durante i miei studi alla Steve Jobs Academy, sede di Catania. Il percorso spazia dalla programmazione a oggetti di base (classi, ereditarietà, interfacce) fino a concetti più avanzati come il multithreading e le interfacce grafiche con Swing.

---

## Sul contenuto di questa repository

Questa repository raccoglie il mio percorso di apprendimento di Java: non tutti i progetti hanno lo stesso livello di complessità, e alcuni nascono come esercizi mirati a un singolo concetto (le eccezioni, il multithreading, le interfacce), mentre altri sono progetti più completi che mettono insieme più argomenti contemporaneamente.

In alcuni progetti, l'interfaccia grafica (GUI) è stata realizzata con il supporto dell'intelligenza artificiale, mentre la logica applicativa sottostante è farina del mio sacco. Lo specifico caso per caso qui sotto, per trasparenza.

---

## Struttura dei progetti

| Cartella | Contenuto |
|---|---|
| `01_parking-threads` | Simulazione di un parcheggio con multithreading: gerarchia di classi auto (benzina, diesel, elettriche), interfaccia `Rechargeable` per i veicoli ricaricabili, eccezione personalizzata per parcheggio pieno |
| `02_soccer` | Gestionale calcistico: creazione di giocatori con nazionalità, arbitri, ruoli e squadre. L'interfaccia grafica (`SoccerGUI`) è stata realizzata con il supporto dell'IA |
| `03_parking` | Parcheggio con `ArrayList`: la classe `StopOver` traccia l'intera sosta di un'auto, dall'ingresso all'uscita, calcolandone il costo in base alla durata |
| `04_removeCtrl` | Esercizio su interfacce e classi astratte, con un esempio pratico di domotica: telecomandi per TV e climatizzatore gestiti tramite un'interfaccia comune |
| `05_Airport` | Simulazione di un registro voli nazionale: caricamento dati da file, filtri di ricerca multipli e interfaccia grafica Swing per l'inserimento e la consultazione dei voli |
| `06_Threads-Formula` | Simulazione di una gara di Formula 1 con multithreading: ogni pilota corre su un thread separato, con tempi giro casuali e ricostruzione dell'ordine d'arrivo |
| `07_exam-project` | Progetto finale: gestionale di un parcheggio con interfaccia grafica, che riassume la maggior parte dei concetti affrontati nel percorso |

---

## Progetti principali

### 05_Airport — Registro voli nazionale

Il progetto più articolato in termini di gestione dati: la classe `DBManager` legge un file di testo riga per riga (con `try-with-resources` e uno `switch` sul tipo di dato) per ricostruire aerei e voli in memoria. Il registro voli (`NationalFlightRegistry`) supporta più livelli di filtro — per aeroporto di partenza, per tratta, per data — ed è consultabile sia da codice (`Main`) sia tramite un'interfaccia grafica Swing (`FlightRegistryUI`) con form di inserimento e pannello dei risultati.

### 07_exam-project — Gestionale parcheggio

Il progetto conclusivo del percorso, pensato per mettere in pratica insieme più argomenti: gestione di una collezione di oggetti (`ArrayList<Car>`), un'interfaccia grafica Swing con tabella, inserimento e modifica dei dati, e gestione degli errori con eccezioni. È il progetto che riflette meglio lo stato attuale delle mie competenze in Java.

---

## Come eseguire i progetti

Ogni cartella contiene un file `Main.java` (o una classe con `main`) da cui partire. Da terminale, dentro la cartella del progetto:
