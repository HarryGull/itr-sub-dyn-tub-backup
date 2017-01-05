/*
 * Copyright 2017 HM Revenue & Customs
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

package mongo

import models.submission.SubmissionResponse
import play.modules.reactivemongo.MongoDbConnection
import reactivemongo.api.DB
import uk.gov.hmrc.mongo.{ReactiveRepository, Repository}
import reactivemongo.bson.BSONObjectID
import reactivemongo.api.commands.WriteConcern

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object InvestmentTaxReliefSubmissionRepository extends MongoDbConnection {
  private lazy val repository = new MongoSubmissionRepository

  def apply() : InvestmentTaxReliefSubmissionRepository = repository
}

//TODO: the model used below would be the model we wish to store - probably subscription data later
trait InvestmentTaxReliefSubmissionRepository extends Repository[SubmissionResponse, BSONObjectID] {
    def wipeTestData() : Future[Unit]
}

class MongoSubmissionRepository(implicit mongo: () => DB)
  extends ReactiveRepository[SubmissionResponse, BSONObjectID]("submissions", mongo, SubmissionResponse.formats)
    with InvestmentTaxReliefSubmissionRepository {

  override def wipeTestData(): Future[Unit] = {
    removeAll(WriteConcern.Acknowledged).map{_.code}
  }
}
