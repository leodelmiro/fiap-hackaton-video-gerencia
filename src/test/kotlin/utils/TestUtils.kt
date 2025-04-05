package utils

import com.leodelmiro.gerencia.core.domain.Envio
import com.leodelmiro.gerencia.core.domain.Status
import java.time.LocalDateTime

fun criaEnvio(
    id: Long? = null,
    status: Status = Status.EM_PROCESSAMENTO,
    url: String? = "test",
    criadoEm: LocalDateTime? = LocalDateTime.now()
) = Envio(
    id = id,
    nome = "Test",
    status = status,
    descricao = "Test",
    autor = "Test",
    url = url,
    criadoEm = criadoEm
)