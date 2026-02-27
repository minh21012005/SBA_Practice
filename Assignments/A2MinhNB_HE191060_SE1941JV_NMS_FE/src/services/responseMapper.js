export function mapId(obj) {
  if (!obj || typeof obj !== "object") return obj;
  const res = { ...obj };

  if (obj.newsArticleId) res.id = obj.newsArticleId;
  if (obj.accountId) res.id = obj.accountId;
  if (obj.categoryId) res.id = obj.categoryId;
  if (obj.tagId) res.id = obj.tagId;

  // map nested category
  if (obj.category) res.category = mapId(obj.category);
  if (obj.createdBy) res.createdBy = mapId(obj.createdBy);
  if (Array.isArray(obj.tags) || obj.tags instanceof Set) {
    res.tags = Array.from(obj.tags || []).map((t) => mapId(t));
  }

  return res;
}

export function unwrapPageResponse(axiosResponse) {
  const data = axiosResponse?.data;
  if (data && data.content && Array.isArray(data.content)) {
    const mapped = data.content.map((item) => mapId(item));
    return { ...axiosResponse, data: mapped };
  }
  return axiosResponse;
}
