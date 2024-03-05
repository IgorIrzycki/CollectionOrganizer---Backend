Projekt stanowi backend aplikacji do zarządzania kolekcjami przedmiotów. Został zbudowany przy użyciu frameworka Spring Boot w języku Java, korzystając z bazy danych MongoDB do przechowywania danych. Zaimplementowano mechanizm szyfrowania haseł oraz uwierzytelniania użytkowników za pomocą tokenu JWT, dzięki któremu zabezpieczono również odpowiednie endpointy dla zapytań HTTP. Podczas implementacji pojawiły się wyzwania związane z wydajnością i czasem oczekiwania na dane z bazy. Przyjęte podejście, wykorzystujące mechanizmy projekcji oraz stronicowania, przyczyniło się do skutecznego zminimalizowania tego problemu.

Na projekt składa się kilka pakietów zawierających klasy lub interfejsy. Zawartości poszczególnych pakietów można przedstawić za pomocą diagramów klas. Poniższy diagram odzwierciedla klasy z pakietu „model”, które są oznaczone adnotacją @Document. Oznacza to, że że obiekty tych klas powinny być zapisywane jako dokumenty w bazie danych MongoDB.

![image](https://github.com/IgorIrzycki/CollectionOrganizer---Backend/assets/97196620/e5913942-6715-4855-8fc6-17425534214b)


W pakiecie „service” znajdują się klasy, które są oznaczone adnotacją @Service. Ta adnotacja wskazuje, że te klasy pełnią rolę komponentów usługowych w systemie. Odpowiedzialne są za realizację logiki biznesowej, obsługę transakcji, oraz inne operacje związane z przetwarzaniem danych. Klasy takie zazwyczaj zawierają metody, które oferują konkretną funkcjonalność dla innych komponentów, takich jak kontrolery. Mogą one wykorzystywać klasy z pakietu „model” do operacji na danych, które są następnie przechowywane w bazie danych MongoDB.

![image](https://github.com/IgorIrzycki/CollectionOrganizer---Backend/assets/97196620/64cff4f3-fd07-47be-b85f-fb120718b29a)


W pakiecie „controller” znajdują się klasy, które są oznaczone adnotacją @RestController. Te klasy pełnią rolę kontrolerów w systemie i są odpowiedzialne za obsługę żądań HTTP oraz udostępnianie interfejsów programistycznych (API) za pomocą protokołu HTTP, zazwyczaj w stylu RESTful. Adnotacja taka wskazuje, że wynikiem działania tych klas są dane, które są automatycznie zamieniane na format odpowiedni dla protokołu HTTP (np. JSON). Kontrolery te są punktem wejścia dla żądań przychodzących od klientów, a ich zadaniem jest przekierowanie tych żądań do odpowiednich komponentów usługowych.

![image](https://github.com/IgorIrzycki/CollectionOrganizer---Backend/assets/97196620/58806630-9fbb-4816-9feb-4e824117e776)


W pakiecie „repository” znajdują się interfejsy, które pełnią istotną rolę w komunikacji między backendem a bazą danych. Interfejsy te rozszerzają interfejsy MongoRepository, co oznacza, że są częścią mechanizmu Spring Data MongoDB, ułatwiającego operacje związane z dostępem do danych w bazie MongoDB, takie jak zapis, odczyt, aktualizacja i usuwanie danych.

![image](https://github.com/IgorIrzycki/CollectionOrganizer---Backend/assets/97196620/a6c04328-ec3d-4a64-af46-099df2fb4c4c)
