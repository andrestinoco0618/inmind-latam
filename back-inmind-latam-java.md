## Descripción
Documentación para verificar el funcionamiento de la api back-inmind-latam-java desarrollada con las herramientas JDK 17, Spring Boot, Spring Redis.

> [!IMPORTANT]
> positionQuestion: Número de posición de la pregunta.
> idQuestionnaire: Id único para cada encuesta.
> idQuestion: Id de la pregunta que se visualizará.
> idQuestionType: Tipo de pregunta.
> title: Texto de la pregunta.
> optionsAnswer: Alternativas para las preguntas.

## Servicios 
### 1. Iniciar cuestionario
El servicio se encarga de crear e iniciar la encuesta en Redis, con el fin de preservar los datos sin alteraciones, asegurando que se mantendrán en su forma original para cada usuario que realice la encuesta simultáneamente.

SERVICIO
```http://IP_INSTANCE_EC2:8080/api/v1/transaction/questionnaire/start?profileType={profileType}```

RESPUESTA
```
{
    "positionQuestion": 1,
    "idQuestionnaire": "7ce2f1c6-fa37-4a46-9975-0539afb72c5f",
    "idQuestion": "P00001",
    "idQuestionType": "TP002",
    "title": "Terminos y Condiciones",
    "optionsAnswer": []
} 
```


### 2. Responder pregunta
El servicio se encarga de responder cada pregunta, se debe enviar el Id correspondiente a la encuesta para preservar los datos. Como respuesta se obtiene la siguiente pregunta hija dependiendo si es pregunta hija por pregunta o alternativa.

SERVICIO
```http://IP_INSTANCE_EC2:8080/api/v1/transaction/questionnaire/response```

PETICIÓN
```
{
    "idQuestionnaire": "7ce2f1c6-fa37-4a46-9975-0539afb72c5f",
    "idQuestion": "P00001",
    "responseAnswer": [],
    "openQuestion": ""
}
```

> [!IMPORTANT]
> idQuestionnaire: Id único para cada encuesta .
> idQuestion: Id de la pregunta que se visualizará.
> responseAnswer: Id de alternativas seleccionadas como respuesta, cuando el tipo de respuesta no requiera contestar un alternativa permite enviarse como nulo.
> openQuestion: Respuesta de las preguntas abiertas, cuando el tipo de respuesta no requiera contestar un alternativa permite enviarse como nulo.

RESPUESTA
```
{
    "positionQuestion": 2,
    "idQuestionnaire": "322852f9-0df7-4022-8f84-66296c2cbd97",
    "idQuestion": "P00002",
    "idQuestionType": "TP001",
    "title": "¡Hola! Queremos conocer un poco más de ti y por lo que estas atravesando actualmente. Al llenar este formulario nos ayudarás a encontrar a tu especialista ideal para que podamos acompañarte en este camino en busca de tu bienestar emocional.\n\n*La información brindada aquí es totalmente confidencial. Solo será utilizada por nosotros y con el especialista que desees contactar.\n\n",
    "optionsAnswer": []
}
```
