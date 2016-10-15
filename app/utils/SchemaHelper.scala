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

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.github.fge.jsonschema.core.report.ProcessingReport
import com.github.fge.jsonschema.main.JsonSchemaFactory
import play.api.Play

import scala.io.Source
import scala.util.{Failure, Success, Try}


object SchemaHelper {

  val schemaFile = "SubmissionSchemaV03"
  lazy val schema = SchemaHelper.getSchema

  def getSchema: String =
  {
    // schema loaded from conf folder
    Source.fromInputStream(Play.classloader(Play.current).getResourceAsStream(schemaFile), "UTF-8").getLines().mkString("\n")
  }

  def validateJson(json: String): Boolean = {

    Try {
      // schema map
      // $COVERAGE-OFF$
      val schemaMapper = new ObjectMapper()
      val factory = schemaMapper.getFactory
      val schemaParser = factory.createParser(schema)
      val schemaJson: JsonNode = schemaMapper.readTree(schemaParser)

      // json to validate
      val jsonMapper = new ObjectMapper()
      val jsonFactory = jsonMapper.getFactory
      val jsonParser = jsonFactory.createParser(json)
      val jsonJson: JsonNode = jsonMapper.readTree(jsonParser)

      // validate
      val schemaFactory = JsonSchemaFactory.byDefault()
      val schemaValidator = schemaFactory.getJsonSchema(schemaJson)
      // $COVERAGE-OFF$


      val report = schemaValidator.validate(jsonJson)

      report.isSuccess

    } match {
      case Success(result) => result
      case Failure(_) => false
    }
  }

  def getJsonValidationReport(json: String): Option[ProcessingReport] = {

    Try {
      // schema map
      val schemaMapper = new ObjectMapper()
      val factory = schemaMapper.getFactory
      val schemaParser = factory.createParser(schema)
      val schemaJson: JsonNode = schemaMapper.readTree(schemaParser)

      // json to validate
      val jsonMapper = new ObjectMapper()
      val jsonFactory = jsonMapper.getFactory
      val jsonParser = jsonFactory.createParser(json)
      val jsonJson: JsonNode = jsonMapper.readTree(jsonParser)

      // validate
      val schemaFactory = JsonSchemaFactory.byDefault()
      val schemaValidator = schemaFactory.getJsonSchema(schemaJson)
      schemaValidator.validate(jsonJson)

    } match {
      case Success(result) => Some(result)
      case Failure(_) => None
    }
  }

}
