package com.rockabits.ascend.data

enum class QuestCategory { DAILY, PHOTO, EXPLORE, WALK }

/**
 * Tipo de prova exigida por uma quest.
 * Na versão 1 só existe FOTO (ver documento A02). Outros tipos ficam para depois.
 */
enum class ProofType { PHOTO }

data class Quest(
    val id: String,
    val title: String,
    val description: String,
    val xp: Int,
    val category: QuestCategory,
    val proof: ProofType = ProofType.PHOTO,
)

/** Quests locais de exemplo. Mais tarde virão do backend (painel web do gestor). */
object QuestRepository {
    const val DAILY_ID = "daily"

    val quests: List<Quest> = listOf(
        Quest(
            id = DAILY_ID,
            title = "Quest Diária",
            description = "Descobre algo novo hoje. Tira uma foto a algo em que nunca tinhas reparado no teu caminho.",
            xp = 150,
            category = QuestCategory.DAILY,
        ),
        Quest(
            id = "novo-olhar",
            title = "Um novo olhar",
            description = "Fotografa um lugar que raramente visitas.",
            xp = 100,
            category = QuestCategory.PHOTO,
        ),
        Quest(
            id = "explora",
            title = "Explora o desconhecido",
            description = "Visita um parque ou local onde nunca estiveste e tira uma foto como prova.",
            xp = 150,
            category = QuestCategory.EXPLORE,
        ),
        Quest(
            id = "vai-mais-longe",
            title = "Vai mais longe",
            description = "Caminha 5 km e tira uma foto no ponto mais distante do teu percurso.",
            xp = 150,
            category = QuestCategory.WALK,
        ),
    )
}
