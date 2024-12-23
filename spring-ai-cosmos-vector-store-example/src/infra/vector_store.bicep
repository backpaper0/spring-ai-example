@description('Cosmos DBのアカウント')
param cosmosAccountName string

@description('Azure OpenAIのアカウント')
param openaiAccountName string

@description('text-embedding-3-smallのデプロイ名')
param embeddingModelDeploymentName string

@description('gpt-4o-miniのデプロイ名')
param chatModelDeploymentName string

param location string = resourceGroup().location

resource cosmosAccount 'Microsoft.DocumentDB/databaseAccounts@2024-11-15' = {
  name: cosmosAccountName
  location: location
  kind: 'GlobalDocumentDB'
  properties: {
    capabilities: [
      {
        name: 'EnableNoSQLVectorSearch'
      }
    ]
    databaseAccountOfferType: 'Standard'
    locations: [
      {
        failoverPriority: 0
        isZoneRedundant: false
        locationName: location
      }
    ]
  }
}

resource openaiAccount 'Microsoft.CognitiveServices/accounts@2024-10-01' = {
  name: openaiAccountName
  kind: 'OpenAI'
  location: location
  sku: {
    name: 's0'
  }
  dependsOn: [
    cosmosAccount
  ]
}

resource embeddingModelDeployment 'Microsoft.CognitiveServices/accounts/deployments@2024-10-01' = {
  parent: openaiAccount
  name: embeddingModelDeploymentName
  properties: {
    model: {
      name: 'text-embedding-3-small'
      version: '1'
      format: 'OpenAI'
    }
  }
  sku: {
    name: 'Standard'
    capacity: 120
  }
}

resource chatModelDeployment 'Microsoft.CognitiveServices/accounts/deployments@2024-10-01' = {
  parent: openaiAccount
  name: chatModelDeploymentName
  properties: {
    model: {
      name: 'gpt-4o-mini'
      version: '2024-07-18'
      format: 'OpenAI'
    }
  }
  sku: {
    name: 'GlobalStandard'
    capacity: 10
  }
  dependsOn: [
    embeddingModelDeployment
  ]
}
