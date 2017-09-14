/*
 * Copyright (C) 2017 VSCT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.vsct.tock.bot.engine

import fr.vsct.tock.bot.connector.Connector
import fr.vsct.tock.bot.definition.BotDefinition
import fr.vsct.tock.bot.engine.action.Action
import fr.vsct.tock.bot.engine.event.Event
import fr.vsct.tock.bot.engine.user.PlayerId
import io.vertx.ext.web.Router

/**
 * Controller to connect [Connector] and [Bot].
 */
interface ConnectorController {

    /**
     * The bot definition served by the controller.
     */
    val botDefinition: BotDefinition

    /**
     * Handle an event sent by the connector. the primary goal of this controller.
     */
    fun handle(event: Event)

    /**
     * Register services at startup.
     */
    fun registerServices(rootPath: String, installer: (Router) -> Unit)

    /**
     * Returns an error message (technical error).
     */
    fun errorMessage(playerId: PlayerId, applicationId: String, recipientId: PlayerId): Action {
        val errorAction = botDefinition.errorAction(playerId, applicationId, recipientId)
        errorAction.metadata.lastAnswer = true
        return errorAction
    }
}