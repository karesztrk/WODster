import { fetchPostContent } from './posts';

export type TagContent = {
  readonly slug: string;
  readonly name: string;
};

const tagMap: { [key: string]: TagContent } = generateTagMap();

function generateTagMap(): { [key: string]: TagContent } {
  let result: { [key: string]: TagContent } = {};
  const tags = listTags();
  if (tags) {
    tags.forEach((tag) => {
      result[tag.slug] = tag;
    });
  }

  return result;
}

export function getTag(slug: string) {
  const mappedTag = tagMap[slug];
  return (
    mappedTag || {
      slug,
      name: slug,
    }
  );
}

export function listTags(limit?: number): TagContent[] {
  const postContent = fetchPostContent();
  const allTags = Array.from(
    new Set<string>(postContent.flatMap((content) => content.tags)),
  );
  const tags = allTags.map((tag) => ({
    name: tag,
    slug: tag,
  }));
  if (!limit) {
    return tags;
  }
  return tags.slice(0, limit);
}
