<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        body, html {
            font-family: 'DejaVu Sans', Arial, Helvetica, sans-serif;
            background-color: beige;
            padding: 2em;
        }

        .counter {
            background-color: burlywood;
            padding: 0.5em;
            color: black;
            font-size: 1.5rem;
        }
    </style>
</head>
<body>
<h1>Esempio di JSP</h1>
<% /** Posso scrivere qui dentro il codice Java **/

    /** Session è una mappa di coppie chiave-valore, dove le chiavi sono
     *   stringhe e i valori sono object. Nell'oggetto session è presente
     *   un oggetto counter? Se sì, lo salvo.
     *   Sicuramente, la prima volta non esiste nessun oggetto counter,
     *   quindi ci si aspetta che la variabile counter valga NULL.
     */

    Integer counter;
    synchronized (session) {
        counter = (Integer) session.getAttribute("counter");
        if (counter == null) {
            session.setAttribute("counter", 1);
            counter = 1;
        } else {
            counter = counter + 1;
            session.setAttribute("counter", counter);
            if (counter == 9) session.invalidate();
        }
    }
%>
<p> Il valore attuale del contatore è <span class="counter"><%= counter %></span></p>

</body>
</html>
