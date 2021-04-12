import { fetchPostContent } from './posts';

export type TagContent = {
  readonly slug: string;
  readonly name: string;
};

export function listTags(limit?: number): TagContent[] {
  const postContent = fetchPostContent();
  if (!postContent || !Array.isArray(postContent)) {
    return [];
  }

  const tagSet = new Set<string>(
    // flatMap() polyfill
    [].concat(
      ...postContent.map((content) =>
        content.tags.filter((t) => t).map((t) => t.toLowerCase()),
      ),
    ),
  );
  const tags = Array.from(tagSet).map((tag) => ({
    name: tag,
    slug: tag,
  }));

  if (limit) {
    return tags.slice(0, limit);
  }
  return tags;
}
