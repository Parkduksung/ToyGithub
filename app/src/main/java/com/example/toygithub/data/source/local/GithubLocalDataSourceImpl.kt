package com.example.toygithub.data.source.local

import com.example.toygithub.room.GithubDao
import com.example.toygithub.room.GithubEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.toygithub.util.Result

class GithubLocalDataSourceImpl @Inject constructor(private val githubDao: GithubDao) :
    GithubLocalDataSource {


    override suspend fun registerGithubEntity(entity: GithubEntity): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext try {
                githubDao.registerGithubEntity(entity) > 0
            } catch (e: Exception) {
                false
            }
        }

    override suspend fun isExistGithubEntityList(): Boolean = withContext(Dispatchers.IO) {
        return@withContext githubDao.getAll().isNotEmpty()
    }


    override suspend fun getAllGithubEntity(): Result<List<GithubEntity>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.Success(githubDao.getAll())
            } catch (e: Exception) {
                Result.Error(Exception("Error getAllSSGEntity!"))
            }
        }

    override suspend fun deleteGithubEntity(entity: GithubEntity): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext try {
                githubDao.deleteGithubEntity(entity.id!!) >= 1
            } catch (e: Exception) {
                false
            }
        }

    override suspend fun isExistGithubEntity(entity: GithubEntity): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext try {
                githubDao.isExistGithubEntity(entity.id!!) >= 1
            } catch (e: Exception) {
                false
            }
        }


    override suspend fun getAllBookmarkEntity(): Result<List<GithubEntity>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val getAllBookmarkList =
                    githubDao.getBookmarkGithubEntityList(true)
                Result.Success(getAllBookmarkList)
            } catch (e: Exception) {
                Result.Error(Exception(Throwable("bookmarkList is Null!")))
            }
        }
}