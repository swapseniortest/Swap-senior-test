package com.swap.issues.recovery.api.v1;

import com.swap.issues.recovery.api.v1.response.IssueRecoveryResponse;
import com.swap.issues.recovery.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "GitHub Recovery Issues")
public interface GitHubSnapshotApi {

    @Operation(description = "Cria um snapshot das issues e contribuidores de determinado projeto no momento em que a requisição foi feita e envia o resultado, assíncronamente (1 dia depois), para o <a href=\"https://weebhook.site\" target=\"_blank\">weebhook.site</a>",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Requisição bem-sucedida. Retorna o snapshot das issues e contribuidores.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IssueRecoveryResponse.class),
                                    examples = {
                                            @ExampleObject(name = "Exemplo de Sucesso", value = """
                                                    {
                                                      "user": "Onwer do repositorio",
                                                      "repository": "Nome do repositorio",
                                                      "issues": [
                                                        {
                                                          "title": "Titulo da issue",
                                                          "author": "Nome do autor",
                                                          "labels": [
                                                            "exemplo-customizado-de-label",
                                                            "outro-exemplo-customizado"
                                                          ]
                                                        },
                                                        {
                                                          "title": "Outra issue",
                                                          "author": "Outro autor",
                                                          "labels": [
                                                            "outra-label",
                                                            "java",
                                                            "reviewed"
                                                          ]
                                                        }
                                                      ],
                                                      "contributors": [
                                                        {
                                                          "name": "Nome real do contribuidor, ex: Raphael Gonzales",
                                                          "user": "Usuario do contribuidor",
                                                          "qtdCommits": 1
                                                        },
                                                        {
                                                          "name": "Nome Contribuidor",
                                                          "user": "usuario contribuidor",
                                                          "qtdCommits": 1
                                                        }
                                                      ]
                                                    }
                                                    """)
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = "API Externa Github indisponível",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class),
                                    examples = {
                                            @ExampleObject(name = "Erro 417", value = """
                                                    {
                                                      "timestamp": "2024-10-14T14:30:00",
                                                      "status": 417,
                                                      "error": "Expectation Failed",
                                                      "message": "A API externa do GitHub está indisponível no momento"
                                                    }
                                                    """)
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Requisições não autorizadas pelo github",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class),
                                    examples = {
                                            @ExampleObject(name = "Erro 403", value = """
                                                    {
                                                      "timestamp": "2024-10-14T14:35:00",
                                                      "status": 403,
                                                      "error": "Forbidden",
                                                      "message": "Você não tem permissão para acessar este recurso no GitHub ou foi bloqueado por exceder o limite de requisições"
                                                    }
                                                    """)
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "408",
                            description = "Atingiu o tempo limite de espera ao chamar um serviço externo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class),
                                    examples = {
                                            @ExampleObject(name = "Erro 408", value = """
                                                    {
                                                      "timestamp": "2024-10-14T14:40:00",
                                                      "status": 408,
                                                      "error": "Request Timeout",
                                                      "message": "A requisição para o GitHub excedeu o tempo limite de espera"
                                                    }
                                                    """)
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro sistêmico inesperado e não tratado.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class),
                                    examples = {
                                            @ExampleObject(name = "Erro 500", value = """
                                                    {
                                                      "timestamp": "2024-10-14T14:00:00",
                                                      "status": 500,
                                                      "error": "Internal Server Error",
                                                      "message": "Erro inesperado no servidor"
                                                    }
                                                    """)
                                    }
                            )
                    )
            })
    IssueRecoveryResponse createSnapShotGitHubIssues(@Parameter(description = "Nome de usuário(owner) do GitHub", required = true) String userName,
                                                     @Parameter(description = "Nome do repositório(publico) do GitHub", required = true) String repositoryName);
}
