# Spring AI with Ollama - Local Model

This project demonstrates how to integrate **Spring AI** with **Ollama** to run large language models locally without relying on external APIs.

## Features

- 🚀 Local LLM execution using Ollama
- 🤖 Spring AI integration for seamless AI capabilities
- 📦 Easy-to-use APIs for model interactions
- 🔒 Privacy-first approach - all processing happens locally
- ⚡ Fast inference with local models

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17+** - [Download Java](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+** - [Download Maven](https://maven.apache.org/download.cgi)
- **Ollama** - [Download Ollama](https://ollama.ai/)

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/vivekpandey532/Spring-ai-ollma-local-model.git
cd Spring-ai-ollma-local-model
```

### 2. Set Up Ollama

Download and install Ollama from [ollama.ai](https://ollama.ai/). Then pull a model:

```bash
ollama pull llama2
# or use another model like mistral, neural-chat, etc.
ollama serve
```

This starts the Ollama server on `http://localhost:11434`

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

## Configuration

Configure your Ollama connection in `application.properties` or `application.yml`:

### application.properties

```properties
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.model=llama2
```

### application.yml

```yaml
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      model: llama2
```

## Available Models

Popular models you can use with Ollama:

- **llama2** - Meta's Llama 2 model
- **mistral** - Mistral AI's efficient model
- **neural-chat** - Intel's neural chat model
- **openchat** - Open-source chat model
- **zephyr** - Hugging Face's Zephyr model

Pull a model using:

```bash
ollama pull <model-name>
```

## Usage Example

```java
import org.springframework.ai.ollama.OllamaEmbeddingClient;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    
    private final OllamaEmbeddingClient embeddingClient;
    
    public AIService(OllamaEmbeddingClient embeddingClient) {
        this.embeddingClient = embeddingClient;
    }
    
    public String generateResponse(String prompt) {
        // Your AI logic here
        return embeddingClient.call(prompt);
    }
}
```

## Project Structure

```
Spring-ai-ollma-local-model/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Dependencies

Key dependencies used in this project:

- **Spring Boot** - Application framework
- **Spring AI** - AI integration library
- **Ollama** - Local LLM runtime

See `pom.xml` for complete dependency list.

## Troubleshooting

### Ollama Connection Issues

- Ensure Ollama is running: `ollama serve`
- Check if Ollama is accessible at `http://localhost:11434`
- Verify firewall settings if running on a different machine

### Model Not Found

```bash
# List available models
ollama list

# Pull a model if not present
ollama pull llama2
```

### Performance Issues

- Use a smaller model for faster inference
- Ensure sufficient RAM for the model (typically 4GB minimum)
- Check system resources while running

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.

## License

This project is open source and available under the [MIT License](LICENSE).

## Resources

- [Spring AI Documentation](https://docs.spring.io/spring-ai/)
- [Ollama Documentation](https://github.com/ollama/ollama)
- [Ollama Models](https://ollama.ai/library)

## Contact

For questions or feedback, feel free to reach out via GitHub issues.

---

**Happy coding! 🎉**
