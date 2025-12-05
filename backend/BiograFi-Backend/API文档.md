# BiograFi-Backend API 文档

## 基本信息

- **服务名称**: BiograFi-Backend
- **端口**: 5002
- **数据存储**: MySQL (生产环境) / H2 (开发环境)
- **认证机制**: 基于 userId 的权限过滤
- **API 前缀**: `/api`

## 健康检查 API

### 1. 健康检查

**端点**: GET /api/health
**描述**: 检查服务状态和数据库连接
**响应**:

```json
{
  "success": true,
  "data": {
    "status": "ok",
    "service": "User Management Server (Spring Boot + MySQL)",
    "database": "connected",
    "timestamp": "2024-01-01T12:00:00.000Z"
  },
  "error": null
}
```

## 用户认证 API

### 1. 用户登录

**端点**: POST /api/login
**描述**: 用户登录
**请求体**:

```json
{
  "username": "username",
  "password": "password"
}
```

**响应**:

```json
{
  "success": true,
  "data": {
    "id": 1,
    "username": "username",
    "email": "email@example.com",
    "isActive": true,
    "lastLoginAt": "2024-01-01T12:00:00",
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  },
  "error": null
}
```

### 2. 用户注册

**端点**: POST /api/register
**描述**: 用户注册
**请求体**:

```json
{
  "username": "username",
  "email": "email@example.com",
  "password": "password"
}
```

**响应**:

```json
{
  "success": true,
  "data": {
    "id": 1,
    "username": "username",
    "email": "email@example.com",
    "isActive": true,
    "lastLoginAt": null,
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  },
  "error": null
}
```

### 3. 更新用户信息

**端点**: PATCH /api/users/:userId
**描述**: 更新用户信息
**请求体**:

```json
{
  "email": "new_email@example.com",
  "password": "new_password"
}
```

**响应**:

```json
{
  "success": true,
  "data": {
    "id": 1,
    "username": "username",
    "email": "new_email@example.com",
    "isActive": true,
    "lastLoginAt": "2024-01-01T12:00:00",
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  },
  "error": null
}
```

### 4. 获取所有用户

**端点**: GET /api/users
**描述**: 获取所有用户列表
**响应**:

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "username": "username",
      "email": "email@example.com",
      "isActive": true,
      "lastLoginAt": "2024-01-01T12:00:00",
      "createdAt": "2024-01-01T12:00:00",
      "updatedAt": "2024-01-01T12:00:00"
    }
  ],
  "error": null
}
```

## 项目管理 API

### 1. 获取用户项目列表

**端点**: GET /api/projects?userId=:userId
**描述**: 获取用户项目列表
**查询参数**:
- userId: 用户ID

**响应**:

```json
{
  "success": true,
  "data": [
    {
      "id": "proj_123",
      "userId": 1,
      "name": "项目名称",
      "description": "项目描述",
      "createdAt": "2024-01-01T12:00:00",
      "updatedAt": "2024-01-01T12:00:00"
    }
  ],
  "error": null
}
```

### 2. 创建项目

**端点**: POST /api/projects
**描述**: 创建项目
**请求体**:

```json
{
  "userId": 1,
  "name": "项目名称",
  "description": "项目描述"
}
```

**响应**:

```json
{
  "success": true,
  "data": {
    "id": "proj_123",
    "userId": 1,
    "name": "项目名称",
    "description": "项目描述",
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  },
  "error": null
}
```

### 3. 更新项目

**端点**: PUT /api/projects/:projectId
**描述**: 更新项目
**请求体**:

```json
{
  "name": "新的项目名称",
  "description": "新的项目描述"
}
```

**响应**:

```json
{
  "success": true,
  "data": {
    "id": "proj_123",
    "userId": 1,
    "name": "新的项目名称",
    "description": "新的项目描述",
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  },
  "error": null
}
```

### 4. 删除项目

**端点**: DELETE /api/projects/:projectId
**描述**: 删除项目
**响应**:

```json
{
  "success": true,
  "data": null,
  "error": null
}
```

## 文档管理 API

### 1. 获取用户文档列表

**端点**: GET /api/documents?userId=:userId&projectId=:projectId
**描述**: 获取用户文档列表
**查询参数**:
- userId: 用户ID
- projectId: 项目ID（可选）

**响应**:

```json
{
  "success": true,
  "data": [
    {
      "id": "doc_123",
      "userId": 1,
      "projectId": "proj_123",
      "name": "文档名称",
      "description": "文档描述",
      "content": "文档内容",
      "author": "作者",
      "createdAt": "2024-01-01T12:00:00",
      "updatedAt": "2024-01-01T12:00:00"
    }
  ],
  "error": null
}
```

### 2. 获取文档详情

**端点**: GET /api/documents/:documentId
**描述**: 获取文档详情
**响应**:

```json
{
  "success": true,
  "data": {
    "id": "doc_123",
    "userId": 1,
    "projectId": "proj_123",
    "name": "文档名称",
    "description": "文档描述",
    "content": "文档内容",
    "author": "作者",
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  },
  "error": null
}
```

### 3. 创建文档

**端点**: POST /api/documents
**描述**: 创建文档
**请求体**:

```json
{
  "userId": 1,
  "projectId": "proj_123",
  "name": "文档名称",
  "description": "文档描述",
  "content": "文档内容",
  "author": "作者"
}
```

**响应**:

```json
{
  "success": true,
  "data": {
    "id": "doc_123",
    "userId": 1,
    "projectId": "proj_123",
    "name": "文档名称",
    "description": "文档描述",
    "content": "文档内容",
    "author": "作者",
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  },
  "error": null
}
```

### 4. 更新文档

**端点**: PUT /api/documents/:documentId
**描述**: 更新文档
**请求体**:

```json
{
  "name": "新的文档名称",
  "description": "新的文档描述",
  "content": "新的文档内容",
  "author": "新的作者"
}
```

**响应**:

```json
{
  "success": true,
  "data": {
    "id": "doc_123",
    "userId": 1,
    "projectId": "proj_123",
    "name": "新的文档名称",
    "description": "新的文档描述",
    "content": "新的文档内容",
    "author": "新的作者",
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  },
  "error": null
}
```

### 5. 删除文档

**端点**: DELETE /api/documents/:documentId
**描述**: 删除文档
**响应**:

```json
{
  "success": true,
  "data": null,
  "error": null
}
```

### 6. 搜索文档

**端点**: GET /api/documents/search?userId=:userId&query=:query&projectId=:projectId
**描述**: 搜索文档
**查询参数**:
- userId: 用户ID
- query: 搜索关键词
- projectId: 项目ID（可选）

**响应**:

```json
{
  "success": true,
  "data": [
    {
      "id": "doc_123",
      "userId": 1,
      "projectId": "proj_123",
      "name": "文档名称",
      "description": "文档描述",
      "content": "文档内容",
      "author": "作者",
      "createdAt": "2024-01-01T12:00:00",
      "updatedAt": "2024-01-01T12:00:00"
    }
  ],
  "error": null
}
```

## 实体标注 API

### 1. 获取文档标注列表

**端点**: GET /api/documents/:documentId/annotations
**描述**: 获取文档的实体标注列表
**响应**:

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "documentId": "doc_123",
      "startIndex": 10,
      "endIndex": 15,
      "label": "人物",
      "textContent": "刘备",
      "createdAt": "2024-01-01T12:00:00"
    }
  ],
  "error": null
}
```

### 2. 添加实体标注

**端点**: POST /api/documents/:documentId/annotations/entity
**描述**: 添加实体标注
**请求体**:

```json
{
  "start": 10,
  "end": 15,
  "label": "人物",
  "text": "刘备"
}
```

**响应**:

```json
{
  "success": true,
  "data": {
    "id": 1,
    "documentId": "doc_123",
    "startIndex": 10,
    "endIndex": 15,
    "label": "人物",
    "textContent": "刘备",
    "createdAt": "2024-01-01T12:00:00"
  },
  "error": null
}
```

### 3. 批量添加实体标注

**端点**: POST /api/documents/:documentId/annotations/entity/bulk
**描述**: 批量添加实体标注
**请求体**:

```json
{
  "annotations": [
    {"start": 10, "end": 15, "label": "人物", "text": "刘备"},
    {"start": 25, "end": 30, "label": "地名", "text": "洛阳"}
  ]
}
```

**响应**:

```json
{
  "success": true,
  "data": {
    "insertedCount": 2
  },
  "error": null
}
```

### 4. 删除实体标注

**端点**: DELETE /api/documents/:documentId/annotations/entity/:annotationId
**描述**: 删除实体标注
**响应**:

```json
{
  "success": true,
  "data": null,
  "error": null
}
```

### 5. 搜索实体标注

**端点**: GET /api/annotations/search?documentId=:documentId&label=:label&text=:text
**描述**: 搜索实体标注
**查询参数**:
- documentId: 文档ID
- label: 标签（可选）
- text: 文本（可选）

**响应**:

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "documentId": "doc_123",
      "startIndex": 10,
      "endIndex": 15,
      "label": "人物",
      "textContent": "刘备",
      "createdAt": "2024-01-01T12:00:00"
    }
  ],
  "error": null
}
```

## 可视化分析 API

### 1. 可视化总览统计

**端点**: GET /api/visualization/overview?documentId=:documentId
**描述**: 获取文档字符统计和各标签数量
**响应**:

```json
{
  "success": true,
  "data": {
    "totalChars": 1324,
    "labelCounts": {
      "人物": 18,
      "地名": 12,
      "时间": 7,
      "器物": 2,
      "概念": 5,
      "其他": 3
    }
  },
  "error": null
}
```

### 2. 地点可视化数据

**端点**: GET /api/visualization/locations?documentId=:documentId
**描述**: 获取地名实体聚合数据及地理坐标
**响应**:

```json
{
  "success": true,
  "data": {
    "success": true,
    "locations": [
      {
        "name": "洛阳",
        "count": 4,
        "coordinates": [112.4540, 34.6197],
        "matchInfo": {
          "original": "洛阳",
          "matched": "洛阳",
          "confidence": "high",
          "type": "historical"
        }
      }
    ]
  },
  "error": null
}
```

### 3. 人物关系图数据

**端点**: GET /api/visualization/relationships?documentId=:documentId
**描述**: 获取人物关系网络数据
**响应**:

```json
{
  "success": true,
  "data": {
    "success": true,
    "centerPerson": "刘备",
    "nodes": [
      {
        "id": "刘备",
        "name": "刘备",
        "isCenter": true,
        "frequency": 12
      }
    ],
    "links": [
      {
        "source": "刘备",
        "target": "关羽",
        "strength": 4,
        "label": "熟悉"
      }
    ]
  },
  "error": null
}
```

### 4. 时间轴数据

**端点**: GET /api/visualization/timeline?documentId=:documentId
**描述**: 获取时间实体序列
**响应**:

```json
{
  "success": true,
  "data": {
    "success": true,
    "events": [
      {
        "index": 0,
        "start": 132,
        "end": 135,
        "text": "建安五年",
        "normalizedTime": "200",
        "context": "...刘备于建安五年起兵..."
      }
    ]
  },
  "error": null
}
```

## 导出与缓存 API

### 1. 导出文档及标注

**端点**: POST /api/export-documents
**描述**: 导出文档及标注
**请求体**:

```json
{
  "documentIds": ["doc_123", "doc_456"],
  "exportFormat": "txt+csv" // 或 "json"
}
```

**响应**:

```json
{
  "success": true,
  "data": {
    "success": true,
    "exportId": "exp_123456",
    "exportCount": 2,
    "exportedFiles": [
      {
        "name": "文档名称.txt",
        "url": "/api/exports/exp_123456/文档名称.txt"
      }
    ]
  },
  "error": null
}
```

### 2. 下载导出文件

**端点**: GET /api/exports/:exportId/:fileName
**描述**: 下载导出的文件
**响应**:

```json
{
  "success": true,
  "data": "文件下载功能开发中，当前文件路径: /api/exports/exp_123456/文档名称.txt",
  "error": null
}
```

### 3. 查询地名坐标缓存

**端点**: GET /api/visualization/locations/cache?name=:name
**描述**: 查询地名坐标缓存
**响应**:

```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "洛阳",
    "lng": 112.4540,
    "lat": 34.6197,
    "matchedName": "洛阳",
    "confidence": "high",
    "updatedAt": "2024-01-01T12:00:00"
  },
  "error": null
}
```

### 4. 更新地名坐标缓存

**端点**: POST /api/visualization/locations/cache
**描述**: 更新地名坐标缓存
**请求体**:

```json
{
  "name": "洛阳",
  "lng": 112.4540,
  "lat": 34.6197,
  "matchedName": "洛阳",
  "confidence": "high"
}
```

**响应**:

```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "洛阳",
    "lng": 112.4540,
    "lat": 34.6197,
    "matchedName": "洛阳",
    "confidence": "high",
    "updatedAt": "2024-01-01T12:00:00"
  },
  "error": null
}
```

## 错误响应格式

所有 API 错误响应格式统一为:

```json
{
  "success": false,
  "data": null,
  "error": "错误信息描述"
}
```

## 状态码说明

- 200: 请求成功
- 201: 资源创建成功
- 400: 请求参数错误
- 401: 未授权
- 404: 资源不存在
- 500: 服务器内部错误
