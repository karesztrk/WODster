import { fetchPostContent } from './posts';

export type TagContent = {
  readonly slug: string;
  readonly name: string;
};

export function listTags(limit?: number): TagContent[] {
  const postContent = fetchPostContent();
  console.log('Received tags: ');
  console.log(postContent.map((content) => content.tags));
  const allTags =
    postContent && Array.isArray(postContent) && postContent.map
      ? Array.from(
          new Set<string>(
            postContent
              .map((content) => content.tags)
              .flat()
              .filter((t) => t)
              .map((t) => t.toLowerCase()),
          ),
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
