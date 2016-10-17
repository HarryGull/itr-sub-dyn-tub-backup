/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package utils

import com.fasterxml.jackson.core.{JsonParseException, JsonProcessingException}
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.github.fge.jsonschema.core.report.ProcessingReport
import com.github.fge.jsonschema.main.{JsonSchema, JsonSchemaFactory}
import play.api.{Logger, Play}

import scala.io.Source
import scala.util.{Failure, Success, Try}

object SchemaHelper {

  val schemaFile = "SubmissionSchemaV03"
  lazy val schema = SchemaHelper.getSchema
  lazy val schemaValidator = getSchemaValidator
  lazy val jsonMapper = new ObjectMapper()
  lazy val jsonFactory = jsonMapper.getFactory

  def getSchema: String = {
    // schema loaded from conf folder
    Source.fromInputStream(Play.classloader(Play.current).getResourceAsStream(schemaFile), "UTF-8").getLines().mkString("\n")
  }

  def getSchemaValidator: JsonSchema = {
    val schemaMapper = new ObjectMapper()
    val factory = schemaMapper.getFactory
    val schemaParser = factory.createParser(schema)
    val schemaJson: JsonNode = schemaMapper.readTree(schemaParser)
    val schemaFactory = JsonSchemaFactory.byDefault()
    schemaFactory.getJsonSchema(schemaJson)
  }

  def validateJson(json: String): Boolean = {

    Try {
      val jsonParser = jsonFactory.createParser(json)
      val jsonJson: JsonNode = jsonMapper.readTree(jsonParser)
      val report = schemaValidator.validate(jsonJson)
      report.isSuccess

    } match {
      case Success(result) => result
      case Failure(e: JsonParseException) => {
        Logger.error(s"getJsonValidationReport: There was an error parsing the Json: ${e.getMessage}")
        false
      }
      case Failure(e: JsonProcessingException) => {
        Logger.error(s"getJsonValidationReport: There was an Json Validator Processing Exception: ${e.getMessage}")
        false
      }
      case Failure(e) => {
        Logger.error(s"getJsonValidationReport: There was an a general exception: ${e.getMessage}")
        false
      }
    }
  }

  def getJsonValidationReport(json: String): Option[ProcessingReport] = {

    Try {
      val jsonParser = jsonFactory.createParser(json)
      val jsonJson: JsonNode = jsonMapper.readTree(jsonParser)
      schemaValidator.validate(jsonJson)

    } match {
      case Success(result) => Some(result)
      case Failure(e: JsonParseException) => {
        Logger.error(s"getJsonValidationReport: There was an error parsing the Json: ${e.getMessage}")
        None
      }
      case Failure(e: JsonProcessingException) => {
        Logger.error(s"getJsonValidationReport: There was an Json Validator Processing Exception: ${e.getMessage}")
        None
      }
      case Failure(e) => {
        Logger.error(s"getJsonValidationReport: There was an a general exception: ${e.getMessage}")
        None
      }
    }
  }

}
