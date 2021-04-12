import { fetchPostContent } from './posts';

export type TagContent = {
  readonly slug: string;
  readonly name: string;
  readonly occurence: number;
};

let tagCache: TagContent[];

export function listTags(limit?: number): TagContent[] {
  if (!tagCache) {
    const postContent = fetchPostContent();
    if (!postContent || !Array.isArray(postContent)) {
      return [];
    }

    const tagsMap: Map<string, number> = []
      // flatMap() polyfill
      .concat(
        ...postContent.map((content) =>
          content.tags.filter((t) => t).map((t) => t.toLowerCase()),
        ),
      )
      // Count occurences of each tag
      .reduce<Map<string, number>>((acc, curr: string) => {
        // acc[curr] = ++acc[curr] || 1;
        const occurence = acc.get(curr) + 1 || 1;
        acc.set(curr, occurence);
        return acc;
      }, new Map<string, number>());
    const tags: TagContent[] = Array.from(tagsMap.entries())
      // Sort them by occurence
      .sort((a, b) => {
        return b[1] - a[1];
      })
      // Turn it into TagContent
      .map(([key, value]) => ({
        name: key,
        slug: key,
        occurence: value,
      }));
    tagCache = tags;
  }
  if (limit) {
    return tagCache.slice(0, limit);
  }
  return tagCache;
}
