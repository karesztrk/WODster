import { fetchPostContent } from './posts';

export type TagContent = {
  readonly slug: string;
  readonly name: string;
};

export function listTags(limit?: number): TagContent[] {
  const postContent = fetchPostContent();
  const allTags =
    postContent && Array.isArray(postContent) && postContent.flatMap
      ? Array.from(
          new Set<string>(postContent.flatMap((content) => content.tags)),
        )
      : [];
  const tags = allTags.map((tag) => ({
    name: tag,
    slug: tag,
  }));
  if (!limit) {
    return tags;
  }
  return tags.slice(0, limit);
}
